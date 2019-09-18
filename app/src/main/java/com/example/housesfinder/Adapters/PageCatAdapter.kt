package com.example.housesfinder.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.housesfinder.Fragments.FragmentCatSave

class PageCatAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val tabNames: ArrayList<String> = ArrayList()
    val fragments: ArrayList<Fragment>  = ArrayList()

    fun setPositionWithFragments(){
        var vente = FragmentCatSave()
        vente.cat = "VENTE"
        fragments.add(vente)
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