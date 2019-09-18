package com.example.housesfinder.Activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.housesfinder.Fragments.FragmentCollectionSave
import com.example.housesfinder.Fragments.FragmentHome
import com.example.housesfinder.Fragments.FragmentNotifications
import com.example.housesfinder.Model.User
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var content: FrameLayout? = null

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        lateinit var USER_ID :String
         var USER_KEY :String=""
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

          //checkUser()

        var Ref: FirebaseDatabase = FirebaseDatabase.getInstance()
        var ref = Ref.getReference("saved_posts").child("user_posts").push()
        USER_KEY=ref.key!!
        ref.setValue(User(auth.currentUser!!.uid,ref.key!!))


        /*Log.i("heresaved_posts","h")

        Ref.child("").child("user_id").setValue(User(uid)).addOnSuccessListener { taskSnapshot ->
            Toast.makeText(this, "Challenge added successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { error ->

            Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()
        }*/

        logout_btn.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Déconnexion")
            builder.setMessage("Etes vous  sûre de vouloir quitter l'application ?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("Oui") { dialog, which ->
                signOut()

            }

            builder.setNegativeButton("Annuler") { dialog, which ->

               dialog.dismiss()
            }

            builder.show()

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
     private fun checkUser ()
    {         var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("saved_posts").
        child("user_posts")



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
                    if (savedAds.child("uid").value== USER_ID)
                    {found=true

                    }


                }

                if (!found )
                {
                    var Ref: FirebaseDatabase = FirebaseDatabase.getInstance()
                    var ref = Ref.getReference("saved_posts").child("user_posts").push()
                    USER_KEY=ref.key!!
                    ref.setValue(User(auth.currentUser!!.uid,ref.key!!))
                }
            }
        }
        ref.addValueEventListener(challengeListener)
    }
}
