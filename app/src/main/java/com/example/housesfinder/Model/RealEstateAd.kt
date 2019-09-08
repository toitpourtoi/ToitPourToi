package com.example.housesfinder.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo



@Entity(tableName = "real_estate_ad_table")

class RealEstateAd {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "user_id")
    var userId : String = "----"

    @ColumnInfo(name = "title")
    var title : String = "Appartement à vendre"

    @ColumnInfo(name = "sate")
    var state : Int = -1

    @ColumnInfo(name = "link")
    var link: String = "https://------------"

    @ColumnInfo(name = "link_img")
    var linkImg: String = "https://------------"

    @ColumnInfo(name = "category")
    var category : String = "Appartement"

    @ColumnInfo(name = "type")
    var type : String = "Vente"

    @ColumnInfo(name = "address")
    var address : String = "----"

    @ColumnInfo(name = "date")
    var date : String = "--/--/----"

    @ColumnInfo(name = "wilaya")
    var wilaya : String = "----"

    @ColumnInfo(name = "area")
    var area : String = "----"

    @ColumnInfo(name = "price")
    var price : String = "----"

    @ColumnInfo(name = "descript")
    var descript : String = "----"

    @ColumnInfo(name = "seller_full_name")
    var sellerFullName : String = "----"

    @ColumnInfo(name = "seller_address")
    var sellerAddress : String = "----"

    @ColumnInfo(name = "seller_phone")
    var sellerPhone : String = "----"

    @ColumnInfo(name = "seller_mail")
    var sellerMail : String = "----"
}
