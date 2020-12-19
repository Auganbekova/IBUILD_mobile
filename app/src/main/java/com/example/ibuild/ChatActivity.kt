package com.example.ibuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ibuild.adapters.MessageAdapter
import com.example.ibuild.data_classes.Chat
import com.example.ibuild.data_classes.Message
import com.example.ibuild.data_classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setupViews()
    }

    private fun setupViews() {
        view_messages.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = false
        }
        val chatID = intent.getStringExtra("data")
        val partnerUsername = intent.getStringExtra("partnerUsername")
        supportActionBar!!.title = partnerUsername

        database.collection("messages")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val messages = querySnapshot?.documents?.map {
                    it.toObject(Message::class.java)
                } as List<Message>

                view_messages.adapter = MessageAdapter(messages.filter { message -> message.chatId == chatID })
            }

        var chat = Chat()
        database.collection("chats")
            .whereEqualTo("id", chatID)
            .addSnapshotListener { value, error ->
                chat = value?.documents?.map {
                    it.toObject(Chat::class.java)
                }!![0] as Chat
            }


        btn_send_message.setOnClickListener {
            val date = Date().toString()
            val message = Message(date,chatID!!, txt_message.text.toString(), auth.currentUser!!.uid, Date())
            txt_message.setText("")
            createMessage(
                message
            )
            updateChat(chat.copy(lastMessage = message.message, lastMessageTimestamp = date))
        }

        Handler().postDelayed({
            view_messages.smoothScrollToPosition(0)
        }, 100)
    }

    private fun createMessage (
        message: Message
    ) {
        database.collection("messages")
            .document(message.timestamp.toString())
            .set(message)
            .addOnSuccessListener {
                Toast.makeText(this, "Sended", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateChat (
        chat: Chat
    ){
        database.collection("chats")
            .document(chat.id)
            .set(chat)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}