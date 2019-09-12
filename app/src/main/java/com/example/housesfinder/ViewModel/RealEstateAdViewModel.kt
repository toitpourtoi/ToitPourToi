package com.example.housesfinder.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.housesfinder.Database.RealEstateAdRoomDatabase
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.Repositories.RealEstateAdRepository
import kotlinx.coroutines.launch

class RealEstateAdViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: RealEstateAdRepository
    val allAds: LiveData<List<RealEstateAd>>

    init {
        val realEstateDao  = RealEstateAdRoomDatabase.getDatabase(application,viewModelScope).realEstateDao()
        repository = RealEstateAdRepository(realEstateDao)
        allAds = repository.allAds
    }


    //if not working use GlobalScope instead of viewModelScope
    fun insert(ad : RealEstateAd) = viewModelScope.launch {
        repository.insert(ad)
    }

    //if not working use GlobalScope instead of viewModelScope
    fun delete(ad : RealEstateAd) = viewModelScope.launch {
        repository.delete(ad)
    }

}