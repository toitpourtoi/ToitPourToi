package com.example.housesfinder.Activities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.housesfinder.Fragments.FragmentCollectionSave
import com.example.housesfinder.Fragments.FragmentHome
import com.example.housesfinder.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var content: FrameLayout? = null


    /*//la liste partagé des annoces :
    companion object {
        var listAnnoce = ArrayList<Annonce>()
        var listSellers = ArrayList<Seller>()
       lateinit var  mainSeller: Seller
    }*/


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

        val fragment = FragmentHome()
        addFragment(fragment)
    }


    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }




}
