package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ibuild.authentication.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Работа"

        if (auth.currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        setupViews()
    }

    private fun setupViews() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener() {
        var selectedFragment: Fragment = WorkFragment(3)

        when (it.itemId) {
            R.id.nav_deals -> {
                selectedFragment = DealsFragment(1)
                supportActionBar!!.title = "Договоры"
            }
            R.id.nav_chats -> {
                selectedFragment = ChatsListFragment(2)
                supportActionBar!!.title = "Чаты"
            }
            R.id.nav_works -> {
                selectedFragment = WorkFragment(3)
                supportActionBar!!.title = "Работа"
            }
            R.id.nav_profile -> {
                selectedFragment = ProfileFragment(4)
                supportActionBar!!.title = "Профиль"
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

        true
    }
}