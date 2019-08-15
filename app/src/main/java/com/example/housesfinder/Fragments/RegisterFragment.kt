package com.example.housesfinder.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.R
import com.example.housesfinder.Model.Seller
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_register, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        google_btn.setOnClickListener {
            MainActivity.mainSeller = Seller(
                "Sihem",
                "Bouhenniche",
                "0551789142",
                "fs@esi.dz"
            )
            startActivity(Intent(activity, MainActivity::class.java))
        }
        facebook_btn.setOnClickListener {
            MainActivity.mainSeller = Seller(
                "Sihem",
                "Bouhenniche",
                "0551789142",
                "fs@esi.dz"
            )
            startActivity(Intent(activity, MainActivity::class.java))
        }

    }

}