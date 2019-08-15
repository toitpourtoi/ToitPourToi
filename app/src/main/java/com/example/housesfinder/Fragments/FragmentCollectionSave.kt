package com.example.housesfinder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentCollectionSave :Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_collection_save, container, false)
        return rootView
    }
}