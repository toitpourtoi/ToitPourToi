package com.example.housesfinder.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.housesfinder.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Adapters.RealEstateAdListAdapter
import com.example.housesfinder.Model.RealEstateAd


class FragmentHome : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.annoncesList)
        val adapter = RealEstateAdListAdapter(context!!)
        var ad = RealEstateAd()
        ad.id = 5
        ad.link = "http://www.annonce-algerie.com/DetailsAnnonceImmobilier.asp?cod_ann=186244"
        ad.userId = MainActivity.USER_ID
        adapter.setAds(arrayListOf(ad))

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }
}
