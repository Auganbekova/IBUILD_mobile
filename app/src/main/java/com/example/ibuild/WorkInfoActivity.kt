package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.ibuild.WorkFragment.Companion.WORKER_ID
import com.example.ibuild.data_classes.Chat
import com.example.ibuild.data_classes.User
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_work_info.*
import kotlinx.android.synthetic.main.master_layout.view.*
import java.util.*

class WorkInfoActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_info)

        val actionbar = supportActionBar

        actionbar!!.title = "Детали объявления"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        setupViews()
    }

    private fun setupViews(){
        val workerId = intent.getStringExtra(WORKER_ID)!!
        val workTitle = intent.getStringExtra("work_title")!!
        btn_start_chat.isVisible = workerId != auth.uid
        btn_go_to_dogovor.isVisible = workerId != auth.uid

        var workInfo: Work = Work()
        database.collection("works")
            .whereEqualTo("userId", workerId)
            .whereEqualTo("title", workTitle)
            .addSnapshotListener { value, error ->
                workInfo = value?.documents?.map {
                    it.toObject(Work::class.java)
                }!![0] as Work
                txt_info_title.text = workInfo.title
                txt_info_category.text = workInfo.category
                txt_info_experience.text = workInfo.experience
                txt_info_self.text = workInfo.selfInfo
                txt_info_price.text = workInfo.price
            }


        var worker: User = User()
        database.collection("users")
            .whereEqualTo("uid", workerId)
            .addSnapshotListener { value, error ->
                worker = value?.documents?.map {
                    it.toObject(User::class.java)
                }!![0] as User
            }
        btn_start_chat.setOnClickListener {
            database.collection("users")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    val users = querySnapshot?.documents?.map { it.toObject(User::class.java) } as List<User>
                    val curUser = users.filter { user -> user.uid == auth.uid }

                    createChat(
                        Chat(auth.currentUser!!.uid + workerId,
                            listOf(auth.currentUser!!.uid, workerId),
                            listOf(curUser[0], worker), "",
                            Date().toString()))
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("data", auth.currentUser!!.uid + workerId)
                    intent.putExtra("partnerUsername", worker.email)
                    startActivity(intent)

                }
        }

        btn_go_to_dogovor.setOnClickListener {
            val intent = Intent(this, SignDealActivity::class.java)
            intent.putExtra("work_owner_id", workerId)
            intent.putExtra("work_title", workInfo.title)
            startActivity(intent)
        }
    }

    private fun createChat (
        chat: Chat
    ) {
        database.collection("chats")
            .document(chat.id)
            .set(chat).addOnSuccessListener {
                Toast.makeText(this, "Chat created", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}