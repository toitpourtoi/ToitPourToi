package com.example.housesfinder.Repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.housesfinder.Dao.RealEstateAdDao
import com.example.housesfinder.Model.RealEstateAd

class RealEstateAdRepository(private val realEstateAdDao: RealEstateAdDao) {

    val allAds: LiveData<List<RealEstateAd>> = realEstateAdDao.getAllAds()

    @WorkerThread
    suspend fun insert(ad: RealEstateAd) {
        realEstateAdDao.insert(ad)
    }

    @WorkerThread
    suspend fun delete(ad: RealEstateAd) {
        realEstateAdDao.delete(ad)
    }
}