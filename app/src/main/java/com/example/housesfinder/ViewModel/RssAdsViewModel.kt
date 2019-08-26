package com.example.housesfinder.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.Repositories.RssAdsRepository

class RssAdsViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: RssAdsRepository
    val allAds: LiveData<List<RealEstateAd>>

    init {

        repository = RssAdsRepository()
        allAds = repository.allAds
    }



}