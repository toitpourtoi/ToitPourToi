package com.example.housesfinder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Adapters.RealEstateAdListAdapter
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RealEstateAdViewModel

class FragmentCollectionSave :Fragment() {
    private lateinit var adViewModel: RealEstateAdViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_collection_save, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.savedList)
        val adapter = RealEstateAdListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)


        // Get a new or existing ViewModel from the ViewModelProvider.
        adViewModel = ViewModelProviders.of(this).get(RealEstateAdViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        adViewModel.allAds.observe(this, Observer { ads ->
            // Update the cached copy of the words in the adapter.
            ads?.let { adapter.setAds(it) }
        })
    }
}