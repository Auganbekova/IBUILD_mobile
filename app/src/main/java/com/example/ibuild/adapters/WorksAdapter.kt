package com.example.ibuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.R
import com.example.ibuild.data_classes.User
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.master_layout.view.*

class WorksAdapter (
    private val works: List<Work> = listOf(),
    private val onItemClick: (Work) -> Unit
): RecyclerView.Adapter<WorksAdapter.ItemViewHolder>() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.master_layout, parent, false))


    override fun getItemCount(): Int = works.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(works[position])
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(work: Work){
            view.txt_work_title.text = work.title
            view.txt_categoty.text = work.category
            view.txt_experience.text = work.experience
            view.txt_description.text = work.selfInfo
            view.txt_price.text = work.price

            database.collection("users")
                .whereEqualTo("uid", work.userId)
                .addSnapshotListener { value, error ->
                    val user = value?.documents?.map {
                        it.toObject(User::class.java)
                    }!![0] as User

                    view.txt_name_master.text = user.name
            }

            view.setOnClickListener{
                onItemClick(work)
            }
        }
    }
}