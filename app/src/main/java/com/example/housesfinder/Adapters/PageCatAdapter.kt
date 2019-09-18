package com.example.housesfinder.Adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.housesfinder.Fragments.AnnonceDetailsInfoFragment
import com.example.housesfinder.Fragments.AnnonceDetailsPhotosFragment
import com.example.housesfinder.Fragments.AnnonceDetailsSellerFragment
import com.example.housesfinder.Fragments.FragmentCatSave
import com.example.housesfinder.Model.RealEstateAd

class PageCatAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val tabNames: ArrayList<String> = ArrayList()
    val fragments: ArrayList<Fragment>  = ArrayList()

    fun setPositionWithFragments(){
        fragments.add(FragmentCatSave())
        tabNames.add("VENTE")
        var fragmentCatSave = FragmentCatSave()
        fragmentCatSave.cat = "LOCATION"
        fragments.add(fragmentCatSave)
        tabNames.add("LOCATION")

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