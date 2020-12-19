package com.example.ibuild

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.adapters.WorksAdapter
import com.example.ibuild.data_classes.Work
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_work.*
import kotlinx.android.synthetic.main.fragment_work.view.*

class WorkFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    companion object {
        const val WORKER_ID = "user_id"
    }

    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)
        setupViews(view, container!!.context)
        return view
    }

    private fun setupViews(view: View, context: Context){
        view.btn_add_work.setOnClickListener {
            val intent = Intent(activity, AddWorkActivity::class.java)
            activity!!.startActivity(intent)
        }

        view.view_masters.layoutManager = LinearLayoutManager(context)

        view.btn_filter.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.filter_dialog_layout, null))
                // Add action buttons
                .setPositiveButton("Найти",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                    })
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
            builder.show()
        }

        createRecycler(view.view_masters, "Все категории", "")

    }

    private fun createRecycler(recycler: RecyclerView, category: String, title: String){
        val base = database.collection("works")

        if (category != "Все категории" && title == ""){

        }
        base.addSnapshotListener { value, error ->
            val works = value?.documents?.map {
                it.toObject(Work::class.java)
            } as List<Work>

            recycler.adapter = WorksAdapter(works, onItemClick = {
                val intent = Intent(activity, WorkInfoActivity::class.java)
                intent.putExtra(WORKER_ID, it.userId)
                startActivity(intent)
            })
        }
    }
}