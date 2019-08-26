package com.example.housesfinder.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.housesfinder.Model.RealEstateAd
import androidx.room.OnConflictStrategy



@Dao
interface RealEstateAdDao {

    @Query("SELECT * FROM real_estate_ad_table WHERE id = :id ")
    fun getSingleAd(id: String): LiveData<RealEstateAd>

    @Query("SELECT * FROM real_estate_ad_table")
    fun getAllAds() : LiveData<List<RealEstateAd>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(realEstateAd: RealEstateAd): Long

    @Query("DELETE FROM real_estate_ad_table")
    fun deleteAll()
}