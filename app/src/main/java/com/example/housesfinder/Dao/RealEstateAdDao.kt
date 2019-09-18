package com.example.housesfinder.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.housesfinder.Model.RealEstateAd


@Dao
interface RealEstateAdDao {

    @Query("SELECT * FROM real_estate_ad_table WHERE id = :id ")
    fun getSingleAd(id: String): LiveData<RealEstateAd>

    @Query("SELECT * FROM real_estate_ad_table")
    fun getAllAds() : LiveData<List<RealEstateAd>>

    @Query("SELECT * FROM real_estate_ad_table WHERE user_id = :user_id")
    fun getAllAdsForUser(user_id:String) : LiveData<List<RealEstateAd>>

    @Query("SELECT * FROM real_estate_ad_table WHERE user_id = :user_id AND category = :cat")
    fun getAllAdsForUserByCat(user_id:String,cat:String) : LiveData<List<RealEstateAd>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(realEstateAd: RealEstateAd): Long

    @Delete
    fun delete(realEstateAd: RealEstateAd): Int

    @Query("DELETE FROM real_estate_ad_table")
    fun deleteAll()
}