package com.example.housesfinder.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.housesfinder.*
import com.example.housesfinder.Fragments.FragmentHome
import com.example.housesfinder.Fragments.FragmentCollectionSave
import com.example.housesfinder.Fragments.FragmentNotifications
import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var content: FrameLayout? = null

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        lateinit var USER_ID :String
        lateinit var adViewModel: RealEstateAdViewModel
    }




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = FragmentHome()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile -> {
                val fragment = FragmentCollectionSave()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.frame_container)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("810538588808-dccn6dg1ncs6oe30rt3r2sms6ck4h3is.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        USER_ID = auth.currentUser!!.uid
        Toast.makeText(this, USER_ID, Toast.LENGTH_SHORT)
        Log.i("UID", USER_ID)

        logout_btn.setOnClickListener {
            signOut()
        }
        notification_btn.setOnClickListener {
            val fragment = FragmentNotifications()
            addFragment(fragment)
        }

        // Get a new or existing ViewModel from the ViewModelProvider.
        adViewModel = ViewModelProviders.of(this).get(RealEstateAdViewModel::class.java)

        val fragment = FragmentHome()
        addFragment(fragment)
    }


    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
                //TODO : inspect error here
           /* .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )*/
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun signOut() {
        //verify first if we are using google or facebook account then deconnect
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this,WelcomeSplashActivity::class.java))
        }
    }
}
