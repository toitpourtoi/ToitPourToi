package com.example.housesfinder.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_annonce_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup






@SuppressLint("ValidFragment")
class AnnonceDetailsInfoFragment(var position : Int,var annonce : RealEstateAd) : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_annonce_details, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val link = annonce.link
        loadData(link)

    }
    fun loadData(link : String){
        if(link.contains("annonce-algerie")){
            if(annonce.title.contains("-")){
                annonce.price = annonce.title.split("-")[1]
                priceDetails.text = annonce.price
            }
        }
        GlobalScope.launch {
            val operation = async(Dispatchers.IO){
                val doc = Jsoup.connect(link).get()
                if(!link.contains("annonce-algerie")){
                    val title = doc.select("#ad-content h1").text()
                    annonce.title = title

                    val description = doc.select("#ad-content p").text()
                    annonce.descript = description
                    val phone = doc.select(".info > ul:nth-child(1) > li:nth-child(1) > strong:nth-child(2) > font:nth-child(2)").text()
                    annonce.sellerPhone = phone
                    var fullname = doc.select(".info > ul:nth-child(1) > li:nth-child(2)").text()
                    if(fullname.contains("Contact:")){
                        fullname = fullname.substring("Contact:".length,fullname.length)
                    }
                    annonce.sellerFullName = fullname
                    var ville = doc.select(".info > ul:nth-child(1) > li:nth-child(4)").text()
                    if(ville.contains("Ville:")){
                        ville = ville.substring("Ville:".length,ville.length)
                    }
                    annonce.address = ville
                    var wilaya = doc.select(".info > ul:nth-child(1) > li:nth-child(3)").text()
                    if(wilaya.contains("Localisation:")){
                        wilaya = wilaya.substring("Localisation:".length,wilaya.length)
                    }
                    annonce.wilaya = wilaya
                    var prix = doc.select(".price > b:nth-child(1)").text()
                    annonce.price = prix
                }
            }

            operation.await()

            launch(Dispatchers.Main) {
                // Stuff that updates the UI
                //fill data
                wilayaDetails.text = annonce.address + " , " + annonce.wilaya
                areaDetails.text = annonce.area
                priceDetails.text = annonce.price
                titreDetails.text = annonce.title
                categorieDetaisl.text = annonce.category
                typeDetails.text = annonce.type
                descriptionDetails.text = annonce.descript
            }
        }

    }

}