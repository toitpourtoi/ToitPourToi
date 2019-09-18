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
import com.example.housesfinder.Adapters.PageAdapter
import com.example.housesfinder.Adapters.PageCatAdapter
import com.example.housesfinder.Adapters.RealEstateAdListAdapter
import com.example.housesfinder.R

import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_annonce_details.*
import kotlinx.android.synthetic.main.fragment_collection_save.*

class FragmentCollectionSave :Fragment() {
    lateinit var pageAdapter: PageCatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageAdapter = PageCatAdapter(activity!!.supportFragmentManager)
        pageAdapter.setPositionWithFragments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_collection_save, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cat_pager.adapter = pageAdapter
        tabs_cat.setupWithViewPager(cat_pager,true)
    }
}