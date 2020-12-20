package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ibuild.data_classes.Deal
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_deal.*

class SignDealActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_deal)

        setupViews()
    }

    private fun setupViews() {
        val workOwner = intent.getStringExtra("work_owner_id")!!
        val workTitle = intent.getStringExtra("work_title")!!

        send_deal_check.setOnClickListener {
            val position = edit_deal_position.text.toString()
            val place = edit_deal_place.text.toString()
            val startDate = edit_deal_start.text.toString()
            val endDate = edit_deal_end.text.toString()

            database.collection("works")
                .whereEqualTo("userId", workOwner)
                .whereEqualTo("title", workTitle)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    val deal = Deal(workOwner, auth.uid!!, workTitle, position, place, startDate, endDate)
                    database.collection("deals")
                        .add(deal).addOnSuccessListener {
                            Toast.makeText(this, "Deal was loaded", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }

                }
        }

    }
}