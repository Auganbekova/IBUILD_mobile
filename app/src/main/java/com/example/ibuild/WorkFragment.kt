package com.example.ibuild

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_work.view.*

class WorkFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View){
        view.btn_add_work.setOnClickListener {
            val intent = Intent(activity, AddWorkActivity::class.java)
            activity!!.startActivity(intent)
        }
    }
}