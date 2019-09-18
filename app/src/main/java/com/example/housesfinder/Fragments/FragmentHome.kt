package com.example.housesfinder.Fragments





import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Activities.WelcomeSplashActivity
import com.example.housesfinder.Adapters.RealEstateAdListAdapter
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RssAdsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.toptas.rssconverter.RssItem




class FragmentHome : Fragment() {


    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    private lateinit var rssAdViewModel: RssAdsViewModel

        private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("810538588808-dccn6dg1ncs6oe30rt3r2sms6ck4h3is.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // [END config_signin]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            googleSignInClient = GoogleSignIn.getClient(context!!, gso)
        }

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.annoncesList)
        val adapter = RealEstateAdListAdapter(this.context!!)
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("saved_posts").child("user_posts")
            .child(MainActivity.USER_KEY).child("saved_posts")


        val  realEstateList : ArrayList<RealEstateAd> =ArrayList()
        rssAdViewModel = ViewModelProviders.of(this).get(RssAdsViewModel::class.java)
        rssAdViewModel.getAds().observe(this.activity!!, Observer <List<RssItem>>{ ads ->
            ads.let{
            for ( item: RssItem in ads)
            {
                val ad = RealEstateAd()

                ad.title=item.title!!
                ad.descript=item.description!!
                ad.link=item.link!!
                ad.date=item.publishDate!!
                if (item.image!=null)
                if ( item.title!!.contains("Wilaya d'")){
                ad.wilaya=item.title!!.substringAfter("Wilaya d'")}
                else{
                    ad.wilaya=item.title!!.substringAfter("Wilaya de")
                }




                val challengeListener = object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                        var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                        var found=false
                        while ((iterator.hasNext())&& (!found)){

                            val savedAds = iterator.next()
                            if (item.link==savedAds.value)
                            {found=true
                                ad.state=1


                            }


                        }
                    }
                }
                ref.addValueEventListener(challengeListener)

                /*if (checkSaved(ad.link)==true){
                    Log.i("result",checkSaved(item.link!!).toString())
                    ad.state=1 }*/


                if(item.title!!.toUpperCase().contains("LOCATION")){
                    ad.category = "LOCATION"
                }else{
                    ad.category = "VENTE"
                }

                realEstateList.add(ad)

            }
            adapter.setAds(realEstateList.toList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context!!)}



            // Update the cached copy of the words in the adapter.
          //  ads?.let {   adapter.setAds(ads) }
        })

      /*  logOutBtn.setOnClickListener {
            signOut()
        }

        notifBtn.setOnClickListener {
            val fragment = FragmentNotifications()
            addFragment(fragment)
            //TODO: use this code to bring data toeachcaegory
            // adapter.setAds(emptyList())
            //  adapter.notifyDataSetChanged()
        }*/




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    private fun addFragment(fragment: Fragment) {


            (context as MainActivity)!!.supportFragmentManager
                .beginTransaction()
                    //TODO : inspect error here
                    /*
                .setCustomAnimations(
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

        /*val adapter = RealEstateAdListAdapter(context!!)
        var ad = RealEstateAd()
        ad.id = 5
        ad.link = "http://www.annonce-algerie.com/DetailsAnnonceImmobilier.asp?cod_ann=186244"
        ad.userId = MainActivity.USER_ID
        adapter.setAds(arrayListOf(ad))

      //  recyclerView.adapter = adapter
       // recyclerView.layoutManager = LinearLayoutManager(context!!)*/

    }
    private fun checkSaved(link:String):Boolean
    { var result:Boolean=false
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("saved_posts").child("user_posts")
            .child(MainActivity.USER_KEY).child("saved_posts")


        val challengeListener = object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                var found=false
                while ((iterator.hasNext())&& (!found)){

                    val savedAds = iterator.next()
                    if (link==savedAds.value)
                    {found=true
                        result=true


                }


            }
    }
        }
        ref.addValueEventListener(challengeListener)
        Log.i("found",result.toString())
      return result

}}
