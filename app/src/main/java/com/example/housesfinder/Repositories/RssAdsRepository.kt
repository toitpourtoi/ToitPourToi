package com.example.housesfinder.Repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesfinder.RssService.RssService
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import me.toptas.rssconverter.RssItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class RssAdsRepository {
    lateinit var allAds: MutableLiveData<List<RssItem>>

  /*  init {
        allAds=listConverter(getAllads())
    }*/




     fun getAllads(baseUrl:String,url:String): MutableLiveData<List<RssItem>>
    {
        allAds= MutableLiveData(emptyList())

        getRssFeed(baseUrl,url,allAds)



        /*var adsLiveData = MutableLiveData(adsData)
        /*adsLiveData.value=adsData*/
            Log.i("info2",adsData.size.toString())*/

           return allAds

    }

    private fun getRssFeed(baseUrl:String,url:String,output :MutableLiveData<List<RssItem>>):List<RssItem>
    {
        var adsData =emptyList<RssItem>()


        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(RssConverterFactory.create())
            .build()
           //GlobalScope.let {
        var service = retrofit.create(RssService::class.java)
       /* CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {*/
        val  response=service.getRss(url)

                        response.enqueue(object : Callback<RssFeed> {
                            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                                // Populate list with response.body().getItems()
                                if (response.isSuccessful())
                                {

                                    adsData=response.body()!!.items!!
                                    output.postValue(adsData)
                                }}

                            override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                                // Show failure message
                                Log.i("error",t.message)
                            }
                        })




        return adsData
    }


}