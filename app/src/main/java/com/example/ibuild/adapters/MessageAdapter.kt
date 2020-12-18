package com.example.ibuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ibuild.R
import com.example.ibuild.data_classes.Message
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.layout_item_message_in.view.*

private val auth by lazy { FirebaseAuth.getInstance() }

class MessageAdapter (
    private val messages: List<Message> = emptyList()
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutResource = if (viewType == 0)
            R.layout.layout_item_message_out
        else
            R.layout.layout_item_message_in

        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(message = messages[position])
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == auth.uid)
            0
        else
            1
    }

    inner class MessageViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bindMessage(message: Message) {
            view.text_view.text = message.message
            view.date.text = message.timestamp.toString()
        }
    }
}