package com.example.ibuild

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ibuild.adapters.ChatAdapter
import com.example.ibuild.authentication.LoginActivity
import com.example.ibuild.data_classes.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_chatlist.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ChatsListFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chatlist, container, false)
        setupViews(view, container!!.context)
        return view
    }

    private fun setupViews(view: View, context: Context){
        view.view_chat_list.layoutManager = LinearLayoutManager(context)
        database.collection("chats")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val chats = querySnapshot?.documents?.map {
                    it.toObject(Chat::class.java)
                } as List<Chat>


                view.view_chat_list.adapter =
                    ChatAdapter(chats.filter { chats -> chats.participantIds[0] == auth.uid || chats.participantIds[1] == auth.uid },
                        onItemClick = {
                            val intent = Intent(activity, ChatActivity::class.java)
                            intent.putExtra("data", it.id)
                            if (it.participantIds[0] == auth.currentUser!!.uid ) {
                                intent.putExtra("partnerUsername", it.participants[1].name+" " + it.participants[1].surname)
                            } else if(it.participantIds[1] == auth.currentUser!!.uid ) {
                                intent.putExtra("partnerUsername", it.participants[0].name+" " + it.participants[0].surname)
                            }

                            startActivity(intent)
                        })
            }
    }
}