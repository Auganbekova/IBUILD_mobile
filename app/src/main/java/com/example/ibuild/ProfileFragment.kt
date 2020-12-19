package com.example.ibuild

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.adapters.MasterWorksAdapter
import com.example.ibuild.authentication.LoginActivity
import com.example.ibuild.data_classes.User
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View){
        var user = User()
            database.collection("users")
            .whereEqualTo("uid", auth.uid)
            .addSnapshotListener { value, error ->
                user = value?.documents?.map {
                    it.toObject(User::class.java)
                }!![0] as User
            }

        view.name_master.text = user.name
        view.surname_master.text = user.surname
        view.email_master.text = user.email

        view.view_profile_masters.layoutManager = LinearLayoutManager(context)
        var finished = false
        var unfinished = false
        createRecycler(view.view_profile_masters, finished, unfinished)

        view.chb_work_finished.setOnCheckedChangeListener { buttonView, isChecked ->
            finished = isChecked
            createRecycler(view.view_profile_masters, finished, unfinished)
        }

        view.chb_work_unfinished.setOnCheckedChangeListener { buttonView, isChecked ->
            unfinished = isChecked
            createRecycler(view.view_profile_masters, finished, unfinished)
        }

        view.btn_sign_out.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity!!.startActivity(intent)
        }
    }

    private fun createRecycler(recycler: RecyclerView, finished: Boolean, unfinished: Boolean){
        val base = database.collection("works").whereEqualTo("userId", auth.uid)

        if (finished && !unfinished){
            base.whereEqualTo("finished", false).addSnapshotListener { value, error ->
                inner(recycler, value)
            }
        } else if (!finished && unfinished){
            base.whereEqualTo("finished", true).addSnapshotListener { value, error ->
                inner(recycler, value)
            }
        } else base.addSnapshotListener { value, error ->
            inner(recycler, value)
        }
    }

    private fun inner(recycler: RecyclerView, value: QuerySnapshot?){
        val works = value?.documents?.map {
            it.toObject(Work::class.java)
        } as List<Work>

        recycler.adapter = MasterWorksAdapter(works, onItemClick = {
            val intent = Intent(activity, WorkInfoActivity::class.java)
            intent.putExtra(WorkFragment.WORKER_ID, it.userId)
            startActivity(intent)
        })
    }
}