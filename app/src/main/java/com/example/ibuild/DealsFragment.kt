package com.example.ibuild

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.ibuild.data_classes.Deal
import com.example.ibuild.data_classes.Work
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_deals.view.*

class DealsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deals, container, false)
        setupViews(view, container!!.context)
        return view
    }

    private fun setupViews(view: View, context: Context) {

        view.terminate_deal_check.setOnClickListener {
            database.collection("deals")
                .whereEqualTo("ownerId", auth.uid)

        }

        database.collection("deals")
            .whereEqualTo("ownerId", auth.uid)
            .addSnapshotListener { value, firebaseFirestoreException ->
                val deal = value?.documents?.map {
                    it.toObject(Deal::class.java)
                } as List<Deal>
                if (deal.isNotEmpty()) {
                    view.edit_signed_deal_position.setText(deal[0].position)
                    view.edit_signed_deal_place.setText(deal[0].place)
                    view.edit_signed_deal_start.setText(deal[0].startDate)
                    view.edit_signed_deal_end.setText(deal[0].endDate)
                } else {
                    database.collection("deals").whereEqualTo("workerId", auth.uid)
                        .addSnapshotListener { valuee, firebaseFirestoreException ->
                            val deall = valuee?.documents?.map {
                                it.toObject(Deal::class.java)
                            } as List<Deal>
                            Log.d("check_signed_deal2", deall.toString())

                            if (deal.isEmpty()) {
                                view.edit_signed_deal_position.setText(deall[0].position)
                                view.edit_signed_deal_place.setText(deall[0].place)
                                view.edit_signed_deal_start.setText(deall[0].startDate)
                                view.edit_signed_deal_end.setText(deall[0].endDate)
                            } else {
                                view.deals_scroll_view.isVisible = false
                                view.txt_jobless.isVisible = true
                            }
                        }
                }


            }
    }
}