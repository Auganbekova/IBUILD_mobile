package com.example.ibuild.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ibuild.MainActivity
import com.example.ibuild.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.title = "Login"

        setUpViews()
    }

    private fun setUpViews() {
        login.setOnClickListener {
            signIn(txt_email.text.toString(), txt_password.text.toString())

        }
        txt_dont_have_account.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)

        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
                return@addOnCompleteListener
            }
            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
        }
    }
}