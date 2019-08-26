package com.example.housesfinder.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.housesfinder.Dao.RealEstateAdDao
import com.example.housesfinder.Model.RealEstateAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                ).fallbackToDestructiveMigration()
                    .addCallback(RealEstateAdDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        private class RealEstateAdDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    GlobalScope.launch {
                        populateDatabase(database.realEstateDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(realEstateAdDao: RealEstateAdDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            realEstateAdDao.deleteAll()

            var ad = RealEstateAd()
            ad.category = "Villa"
            ad.wilaya = "Alger"
            ad.price = "1.000.000 DA"
            realEstateAdDao.insert(ad)

        }
    }

}