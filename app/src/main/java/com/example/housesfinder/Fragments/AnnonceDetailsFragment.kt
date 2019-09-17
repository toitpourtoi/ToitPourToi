package com.example.housesfinder.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.housesfinder.*
import com.example.housesfinder.Adapters.PageAdapter
import com.example.housesfinder.Model.RealEstateAd
import kotlinx.android.synthetic.main.activity_annonce_details.*

class AnnonceDetailsFragment : Fragment() {
    var idAd: Int = 0
    lateinit var annonce:RealEstateAd
    lateinit var pageAdapter: PageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageAdapter = PageAdapter(activity!!.supportFragmentManager)
        pageAdapter.setPositionWithFragments(idAd,annonce)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.activity_annonce_details, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = pageAdapter
        tabs.setupWithViewPager(view_pager,true)
        Log.i("DETAILS",idAd.toString())
        Log.i("PAGER",pageAdapter.count.toString())
    }

}
