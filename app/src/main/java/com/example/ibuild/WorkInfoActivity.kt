package com.example.ibuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_work_info.*

class WorkInfoActivity : AppCompatActivity() {
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
        btn_start_chat.setOnClickListener {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}