package com.example.housesfinder.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Adapters.RealEstateAdListAdapter
import com.example.housesfinder.R

import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_collection_save.*

class FragmentCatSave :Fragment() {

    var cat = "VENTE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_display_save, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.savedList)
        val adapter = RealEstateAdListAdapter(context!!)
        if(this.cat == "VENTE"){
            MainActivity.adViewModel.allAdsVente.observe(this, Observer { ads ->
                // Update the cached copy of the words in the adapter.
                Log.i("LENGHT",ads.size.toString())
                Log.i("USERID",MainActivity.USER_ID)
                ads?.let { adapter.setAds(it) }
            })
        }else{
            MainActivity.adViewModel.allAdsLocation.observe(this, Observer { ads ->
                // Update the cached copy of the words in the adapter.
                Log.i("LENGHT",ads.size.toString())
                Log.i("USERID",MainActivity.USER_ID)
                ads?.let { adapter.setAds(it) }
            })
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }
}