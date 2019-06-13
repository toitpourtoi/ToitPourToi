package com.example.housesfinder.Fragments

import android.os.Bundle
import android.print.PageRange
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.housesfinder.*
import com.example.housesfinder.Adapters.PageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_annonce_details.*
import java.text.FieldPosition

class AnnonceDetailsFragment : Fragment() {
    var position: Int = 0
    lateinit var pageAdapter: PageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageAdapter = PageAdapter(activity!!.supportFragmentManager)
        pageAdapter.setPositionWithFragments(position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.activity_annonce_details, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = pageAdapter
        tabs.setupWithViewPager(view_pager,true)
        Log.i("DETAILS",position.toString())
        Log.i("PAGER",pageAdapter.count.toString())
    }

}
