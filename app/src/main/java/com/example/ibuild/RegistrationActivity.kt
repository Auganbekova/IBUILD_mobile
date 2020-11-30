package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private val auth by lazy{
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setUpViews()
    }

    private fun signUp(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return@addOnCompleteListener

            }
            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpViews() {

        txt_email.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                if (txt_email.text.toString().length < 6){
                    Log.d("checklength", "no")
                }
                else{
                    Log.d("checklength","yes")
                }
            }

        }

        register.setOnClickListener {
            val email = txt_email.text.toString()
            val password = txt_password.text.toString()
            signUp(email, password)

        }

    }
}