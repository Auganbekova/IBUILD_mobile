package com.example.ibuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.R
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.master_works_layout.view.*


class MasterWorksAdapter (
    private val works: List<Work> = listOf(),
    private val onItemClick: (Work) -> Unit
): RecyclerView.Adapter<MasterWorksAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.master_works_layout, parent, false))


    override fun getItemCount(): Int = works.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(works[position])
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(work: Work){
            view.txt_work_title.text = work.title
            view.txt_category.text = work.category

            view.setOnClickListener{
                onItemClick(work)
            }
        }
    }
}