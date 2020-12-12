package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.title = "Profile"

        setupViews()
    }

    private fun setupViews() {
        btn_profile_work.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        btn_profile_chat.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        btn_sign_out.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

}