package com.example.housesfinder.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.housesfinder.Adapters.ViewPagerAdapter
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_annonce_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("ValidFragment")
class AnnonceDetailsPhotosFragment(var position : Int, var annonce : RealEstateAd) : Fragment(){
    lateinit var viewPager : ViewPager
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_annonce_photos, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val link = annonce.link
        loadData(link)



    }
    fun loadData(link : String){
        GlobalScope.launch {
            var images = ArrayList<String>()
            val operation = async(Dispatchers.IO){
                val doc = Jsoup.connect(link).get()
                if(link.contains("annonce-algerie")){
                    val title = doc.select("table.da_rub_cadre:nth-child(3) .da_entete td").text()
                    Log.i("SCRAP",title)// <2>
                }else {
                    var i = 1
                    while(!doc.select(".picture > a:nth-child($i) > figure:nth-child(1) > img:nth-child(1)").isEmpty()){
                        val url = doc.select(".picture > a:nth-child($i) > figure:nth-child(1) > img:nth-child(1)").attr("src")
                        Log.i("SCRAP",url)
                        images.add(url)
                        i++
                    }

                }
            }

            operation.await()

            launch(Dispatchers.Main) {
                viewPager = activity!!.findViewById<ViewPager>(R.id.viewPager)
                adapter = ViewPagerAdapter(context!!, images, 2f)
                viewPager.adapter = adapter
            }
        }

    }

}