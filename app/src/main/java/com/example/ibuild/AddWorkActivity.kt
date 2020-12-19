package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ibuild.data_classes.Category
import com.example.ibuild.data_classes.User
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_work.*
import androidx.fragment.app.FragmentManager as FragmentManager

class AddWorkActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)

        val actionbar = supportActionBar

        actionbar!!.title = "Добавить объявление"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        setupViews()
    }

    private fun setupViews(){


        btn_add.setOnClickListener {
            val title = txt_title.text.toString()
            val experience = txt_experience.text.toString()
            val self = txt_self.text.toString()
            val price = txt_price.text.toString()
            val selectedCategory = spin_category.selectedItem.toString()

            val work = Work(auth.uid!!, title, experience, self, price, selectedCategory, false)
            database.collection("works")
                .add(work).addOnSuccessListener {
                    Toast.makeText(this, "Work was loaded", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}