package com.example.housesfinder.Repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.RssService.RssFeed
import com.example.housesfinder.RssService.RssItem
import com.example.housesfinder.RssService.RssService
import me.toptas.rssconverter.RssConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RssAdsRepository {
    val allAds: LiveData<List<RealEstateAd>>
    init {
        allAds=listConverter(getAllads())
    }




    private fun getAllads(): LiveData<List<RssItem>>
    {
        val adsData = MutableLiveData<List<RssItem>>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.algerimmo.com/")
            .addConverterFactory(RssConverterFactory.create())
            .build()

        val service = retrofit.create(RssService::class.java)
        service.getRss("rss/?category=&type=0&location=")
            .enqueue(object : Callback<RssFeed> {
                override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                    // Populate list with response.body().getItems()
                    adsData.value=response.body()!!.items



                }

                override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                    // Show failure message
                    Log.i("error",t.message)
                }
            })

           return adsData

    }

    private  fun listConverter (rssList:LiveData<List<RssItem>>):LiveData<List<RealEstateAd>>
    {
       var  realEstateList : ArrayList<RealEstateAd>?  = null
        val  realEstateLiveData = MutableLiveData<List<RealEstateAd>>()
        if (rssList.value!=null){
        for ( item:RssItem in rssList.value!!)
        {
            val ad = RealEstateAd()
            ad.address=item.address!!
            ad.descript=item.description!!

            realEstateList!!.add(ad)

        }
            realEstateLiveData!!.value = realEstateList



    }

    return realEstateLiveData
    }

}