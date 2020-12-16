package com.example.ibuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.ibuild.data_classes.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_work.*

class AddWorkActivity : AppCompatActivity() {

    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)

        setupViews()
    }

    override fun setupViews(){
        var categoryList: List<Category>
        database.collection("categories").addSnapshotListener { querySnapshot, error ->
            categoryList = querySnapshot?.documents?.map {
                it.toObject(Category::class.java) } as List<Category>
        }

//        ArrayAdapter.createFromResource(
//            this,
//            arrayOf("asd"),
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spin_category.adapter = adapter
//        }

        btn_add.setOnClickListener {
            val title = txt_title.text.toString()
            val experience = txt_experience.text.toString()
            val self = txt_self.text.toString()
            val price = txt_price.text.toString()


        }
    }
}