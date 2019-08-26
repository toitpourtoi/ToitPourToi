package com.example.housesfinder.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "real_estate_ad_table")

class RealEstateAd {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "category")
    var category : String = "Appartement"

    @ColumnInfo(name = "type")
    var type : String = "Vente"

    @ColumnInfo(name = "address")
    var address : String = "----"

    @ColumnInfo(name = "town")
    var town : String = "----"

    @ColumnInfo(name = "wilaya")
    var wilaya : String = "----"

    @ColumnInfo(name = "area")
    var area : String = "----"

    @ColumnInfo(name = "price")
    var price : String = "----"

    @ColumnInfo(name = "descript")
    var descript : String = "----"

    @ColumnInfo(name = "seller_first_name")
    var sellerFirstName : String = "----"

    @ColumnInfo(name = "seller_last_name")
    var sellerLastName : String = "----"

    @ColumnInfo(name = "seller_address")
    var sellerAddress : String = "----"

    @ColumnInfo(name = "seller_phone")
    var sellerPhone : String = "----"

    @ColumnInfo(name = "seller_mail")
    var sellerMail : String = "----"
}
