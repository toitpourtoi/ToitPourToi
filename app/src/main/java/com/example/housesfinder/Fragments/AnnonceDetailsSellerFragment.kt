package com.example.housesfinder.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

@SuppressLint("ValidFragment")
class AnnonceDetailsSellerFragment(val position: Int) : Fragment() {
    private lateinit var  annonce: RealEstateAd
    private lateinit var adViewModel: RealEstateAdViewModel

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
        adViewModel = ViewModelProviders.of(this).get(RealEstateAdViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        adViewModel.allAds.observe(this, Observer { ads ->
            // Update the cached copy of the words in the adapter.
            annonce = ads!!.get(position)
            fullName.text = annonce.sellerFullName
            phoneProfile.text = annonce.sellerPhone
            emailProfile.text = annonce.sellerMail


            callSeller.setOnClickListener(View.OnClickListener {
                Toast.makeText(this.context,"Calling",Toast.LENGTH_SHORT).show()
                callPhone()
            })
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