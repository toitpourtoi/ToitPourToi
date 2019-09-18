package com.example.housesfinder.Model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var uid:String,var key:String)
