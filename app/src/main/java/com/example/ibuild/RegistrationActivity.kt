package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ibuild.data_classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.title = "Registration"

        setUpViews()
    }

    private fun setUpViews() {
        txt_email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (txt_email.text.toString().length < 6) {
                    Log.d("checklength", "no")
                } else {
                    Log.d("checklength", "yes")
                }
            }
        }

        register.setOnClickListener {
            val name = txt_name.text.toString()
            val surname = txt_surname.text.toString()
            val email = txt_email.text.toString()
            val password = txt_password.text.toString()
            signUp(email, name, surname, password)
        }
    }

    private fun signUp(email: String, name: String, surname: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                loadUserData(
                    task.result?.user?.uid!!,
                    User(task.result?.user?.uid!!, email, name, surname)
                )
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return@addOnCompleteListener
            }
            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun loadUserData(
        id: String,
        user: User
    ) {
        database.collection("users")
            .document(id)
            .set(user).addOnSuccessListener {
                Toast.makeText(this, "Student data loaded", Toast.LENGTH_LONG).show()
            }
    }
}