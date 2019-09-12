package com.example.housesfinder.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.housesfinder.Dao.RealEstateAdDao
import com.example.housesfinder.Model.RealEstateAd
import kotlinx.coroutines.CoroutineScope

@Database(entities = [RealEstateAd::class], version = 1)
public abstract class RealEstateAdRoomDatabase : RoomDatabase() {
    abstract fun realEstateDao(): RealEstateAdDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateAdRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): RealEstateAdRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RealEstateAdRoomDatabase::class.java,
                    "real_estate_ads_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}