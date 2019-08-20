package com.example.housesfinder.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Activities.WelcomeSplashActivity
import com.example.housesfinder.Adapters.AnnonceAdapter
import com.example.housesfinder.R
import com.example.housesfinder.RssService.RssService
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.*
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class FragmentHome : Fragment() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    var adapter: AnnonceAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnnonceAdapter(MainActivity.listAnnoce, this!!.activity!!)
        // [START config_signin]
        // Configure Google Sign In
      /*  val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(context!!, gso)*/

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        getRssFeed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        annoncesList.adapter = adapter
        logout_btn.setOnClickListener {
            signOut()
        }
        notification_btn.setOnClickListener {
            val fragment = FragmentNotifications()
            addFragment(fragment)
        }
    }
    private fun addFragment(fragment: Fragment) {

        (context as MainActivity)!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun signOut() {
        //verify first if we are using google or facebook account then deconnect
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(activity!!) {
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            startActivity(Intent(activity, MainActivity::class.java))
        } else {
            startActivity(Intent(activity,WelcomeSplashActivity::class.java))
        }
    }

    private fun getRssFeed()
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.algerimmo.com/")
            .addConverterFactory(RssConverterFactory.create())
            .build()

        val service = retrofit.create(RssService::class.java)
        service.getRss("rss/?category=&type=0&location=")
            .enqueue(object : Callback<RssFeed> {
                override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                    // Populate list with response.body().getItems()
                    val size = response.body()!!.items!!.size
                    val a=response.body()!!.items!!.get(0).publishDate
                    Log.i("size : ",size.toString())
                    Log.i("first item :",a.toString())


                }

                override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                    // Show failure message
                    Log.i("error",t.message)
                }
            })
    }
}
