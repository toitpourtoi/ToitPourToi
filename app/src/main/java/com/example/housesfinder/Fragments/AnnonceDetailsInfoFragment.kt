package com.example.housesfinder.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.housesfinder.Adapters.ViewPagerAdapter
import com.example.housesfinder.MainActivity
import com.example.housesfinder.Model.Annonce
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_annonce_details.*

@SuppressLint("ValidFragment")
class AnnonceDetailsInfoFragment(var position : Int) : Fragment(){
    lateinit var viewPager : ViewPager
    lateinit var adapter: ViewPagerAdapter
    lateinit var bundle:Bundle
    private lateinit var  annonce: Annonce
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        annonce = MainActivity.listAnnoce.get(position)
        adapter = ViewPagerAdapter(this!!.context!!, annonce!!.image!!, 2f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_annonce_details, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = activity!!.findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        typeDetails.text = annonce.type.toString()
        priceDetails.text = annonce.price.toString() + "DA"
        wilayaDetails.text = annonce.wilaya.toString()
        areaDetails.text = annonce.area.toString() + "m²"
        descriptionDetails.text = annonce.description

    }

}