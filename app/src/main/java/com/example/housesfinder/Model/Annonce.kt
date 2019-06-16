package com.example.housesfinder.Model

import android.net.Uri
import java.util.*


import java.text.SimpleDateFormat

class Annonce(
    area: Double,
    wilaya: String,
    price: Double,
    type: String,
    image: ArrayList<Uri>,
    seller: Seller,
    description: String
) {
    var area : Double ?= area
    var wilaya : String ?= wilaya
    var price : Double ?= price
    var type : String ?= type
    var image : ArrayList<Uri> ?= image
    var seller : Seller?= seller
    var description : String = description
    var date : String = Calendar.getInstance().time.toString("dd/MM/yyyy HH:mm")

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

}