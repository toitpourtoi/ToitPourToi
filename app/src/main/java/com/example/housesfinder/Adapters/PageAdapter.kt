package com.example.housesfinder.Adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.housesfinder.Fragments.AnnonceDetailsInfoFragment
import com.example.housesfinder.Fragments.AnnonceDetailsSellerFragment
import com.example.housesfinder.Model.RealEstateAd

class PageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var position : Int = 0
    val tabNames: ArrayList<String> = ArrayList()
    val fragments: ArrayList<Fragment>  = ArrayList()

    fun setPositionWithFragments(position: Int,annonce : RealEstateAd){
        this.position = position
        fragments.add(AnnonceDetailsInfoFragment(position,annonce))
        tabNames.add("Sur l'immobilier")
        fragments.add(AnnonceDetailsSellerFragment(position,annonce))
        tabNames.add("A propos du vendeur")
        Log.i("SIZE",fragments.size.toString())
    }
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabNames[position]
    }

}