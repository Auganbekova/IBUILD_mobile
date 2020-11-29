package com.example.ibuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setUpViews()
    }
    private fun setUpViews(){
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
    }
}