package com.example.ibuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.R
import com.example.ibuild.data_classes.Chat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_layout.view.*

private val auth by lazy { FirebaseAuth.getInstance() }

class ChatAdapter (
    private val chats: List<Chat> = listOf(),
    private val onItemClick: (Chat) -> Unit
): RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_layout, parent, false))


    override fun getItemCount(): Int = chats.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(chats[position])
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(chats: Chat){
            if (chats.participantIds[0] == auth.currentUser!!.uid) {
                view.chat_username.text = chats.participants[1].email
            } else if (chats.participantIds[1] == auth.currentUser!!.uid) {
                view.chat_username.text = chats.participants[0].email
            }

            view.chat_last_message.text = chats.lastMessage
            view.chat_date.text = chats.lastMessageTimestamp.toGMTString()

            view.setOnClickListener{
                onItemClick(chats)
            }
        }
    }
}