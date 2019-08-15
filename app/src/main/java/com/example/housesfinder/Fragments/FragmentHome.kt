package com.example.housesfinder.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Adapters.AnnonceAdapter
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.appcompat.widget.PopupMenu


class FragmentHome : Fragment() {


    var adapter: AnnonceAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnnonceAdapter(MainActivity.listAnnoce, this!!.activity!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        annoncesList.adapter = adapter
        more_btn.setOnClickListener {
            val menu = PopupMenu(context!!, it)
            menu.getMenu().add("Ajouter wilaya préféré")
            menu.show()
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
}
