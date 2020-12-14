package com.example.ibuild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Work"

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
        var selectedFragment: Fragment = DealsFragment(1)

        when (it.itemId) {
            R.id.nav_deals -> {
                selectedFragment = DealsFragment(1)
            }
            R.id.nav_chats -> {
                selectedFragment = ChatsListFragment(2)
            }
            R.id.nav_works -> {
                selectedFragment = WorkFragment(3)
            }
            R.id.nav_profile -> {
                selectedFragment = ProfileFragment(4)
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

        true
    }
}