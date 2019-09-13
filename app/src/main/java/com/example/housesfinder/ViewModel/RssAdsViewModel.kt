package com.example.housesfinder.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.housesfinder.Repositories.RssAdsRepository
import me.toptas.rssconverter.RssItem

class RssAdsViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: RssAdsRepository
    //private val allAds: LiveData<List<RealEstateAd>>

    init {

        repository = RssAdsRepository()
        //allAds = repository.allAds

    }

    fun getAds(): MutableLiveData<List<RssItem>>
    {
        val second_list = repository.getAllads("https://www.algerimmo.com/","rss/?category=&type=0&location=")
        val first_list =repository.getAllads("http://www.annonce-algerie.com/","upload/flux/rss_1.xml")
        var  mediatorLiveData:MediatorLiveData<List<RssItem>> = MediatorLiveData()
        mediatorLiveData.addSource(first_list){mediatorLiveData.postValue(first_list.value)}
        mediatorLiveData.addSource(second_list){mediatorLiveData.postValue(second_list.value)}

        return mediatorLiveData}



}