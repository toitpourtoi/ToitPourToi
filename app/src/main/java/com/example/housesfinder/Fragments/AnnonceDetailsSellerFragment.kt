package com.example.housesfinder.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@SuppressLint("ValidFragment")
class AnnonceDetailsSellerFragment(val position: Int,var annonce : RealEstateAd) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_profile, container, false)
        return rootView
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set the data
        //get the annonce from database get by id
        val link = annonce.link
        loadData(link)

        callSeller.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.context,"Calling",Toast.LENGTH_SHORT).show()
            callPhone()
        })
    }


    //for calling
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this!!.context!!,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this!!.activity!!,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this!!.activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    fun loadData(link : String){
        GlobalScope.launch {
            val operation = async(Dispatchers.IO){
                val doc = Jsoup.connect(link).get()
                if(link.contains("annonce-algerie")){
                    val title = doc.select("table.da_rub_cadre:nth-child(3) .da_entete td").text()
                    Log.i("SCRAP",title)// <2>
                }else{
                    val phone = doc.select(".info > ul:nth-child(1) > li:nth-child(1) > strong:nth-child(2) > font:nth-child(2)").text()
                    annonce.sellerPhone = phone
                    var fullname = doc.select(".info > ul:nth-child(1) > li:nth-child(2)").text()
                    if(fullname.contains("Contact:")){
                        fullname = fullname.substring("Contact:".length,fullname.length)
                    }
                    annonce.sellerFullName = fullname
                }
            }

            operation.await()

            launch(Dispatchers.Main) {
                // Stuff that updates the UI
                //fill data
                fullName.text = annonce.sellerFullName
                phoneProfile.text = annonce.sellerPhone
                emailProfile.text = annonce.sellerMail
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){

        // get the number from annonce
        //TODO

        val number =  annonce.sellerPhone
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number))
        startActivity(intent)
    }
}