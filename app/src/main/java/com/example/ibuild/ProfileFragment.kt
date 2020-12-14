package com.example.ibuild

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View){
        view.btn_sign_out.setOnClickListener {
            Log.d("asdasd", "Signing out brot")
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity!!.startActivity(intent)
        }
    }
}