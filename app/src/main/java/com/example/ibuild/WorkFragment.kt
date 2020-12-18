package com.example.ibuild

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ibuild.adapters.WorksAdapter
import com.example.ibuild.data_classes.Work
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_work.*
import kotlinx.android.synthetic.main.fragment_work.view.*

class WorkFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

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

        database.collection("works").addSnapshotListener { value, error ->
            val works = value?.documents?.map {
                it.toObject(Work::class.java)
            } as List<Work>

            view.view_masters.adapter = WorksAdapter(works, onItemClick = {
                val intent = Intent(activity, WorkInfoActivity::class.java)
                startActivity(intent)
            })
        }

    }
}