package com.example.housesfinder.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.housesfinder.Adapters.ViewPagerAdapter
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import kotlinx.android.synthetic.main.fragment_annonce_details.*
import java.util.*

@SuppressLint("ValidFragment")
class AnnonceDetailsInfoFragment(var position : Int,var annonce : RealEstateAd) : Fragment(){
    lateinit var viewPager : ViewPager
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        adapter = ViewPagerAdapter(this!!.context!!, ArrayList(), 2f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_annonce_details, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = activity!!.findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        //fill data
        wilayaDetails.text = annonce.address + "," + "," + annonce.wilaya
        areaDetails.text = annonce.area
        priceDetails.text = annonce.price + " DA"
        titreDetails.text = annonce.title
        categorieDetaisl.text = annonce.category
        typeDetails.text = annonce.type
        descriptionDetails.text = annonce.descript


    }

}