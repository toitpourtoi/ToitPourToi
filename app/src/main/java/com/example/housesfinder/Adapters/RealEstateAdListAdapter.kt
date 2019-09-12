package com.example.housesfinder.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Database.RealEstateAdRoomDatabase
import com.example.housesfinder.Fragments.AnnonceDetailsFragment
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import com.example.housesfinder.ViewModel.RealEstateAdViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RealEstateAdListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RealEstateAdListAdapter.AdViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ads = emptyList<RealEstateAd>() // Cached copy of words
    private val adapterContext = context
    lateinit var adViewModel: RealEstateAdViewModel

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adItemView: TextView = itemView.findViewById(R.id.cardTitle)
        val wilayaItemView : TextView = itemView.findViewById(R.id.cardWilaya)
        val linkItemView : TextView = itemView.findViewById(R.id.linkDetails)
        val dateItemView : TextView = itemView.findViewById(R.id.dateDetails)
        val detailsBtn : ImageButton = itemView.findViewById(R.id.seeDetails)
        val saveBtn : ImageButton = itemView.findViewById(R.id.book_btn)
        val moreBtn : ImageButton = itemView.findViewById(R.id.more_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemView = inflater.inflate(R.layout.card_annonce, parent, false)
        return AdViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val current = ads[position]
        holder.adItemView.text = current.title
        holder.wilayaItemView.text = current.wilaya
        holder.linkItemView.text = current.link
        holder.dateItemView.text = current.date

        if(ads[position].state == 1){
            holder.saveBtn.setBackgroundResource(R.drawable.ic_bookmark_black_24dp)
        }
        //set actions
        holder.detailsBtn.setOnClickListener {
            showDetails(position)
        }

        //save annonce
        holder.saveBtn.setOnClickListener {
            saveAnnonce(holder.saveBtn,position)
        }
        //display menu when clicking on moreBtn
        holder.moreBtn.setOnClickListener {
            var popupMenu = PopupMenu(adapterContext,it)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            if(ads[position].state == 1){
                popupMenu.menu.getItem(0).setTitle("Supprimer")
            }
            popupMenu.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    R.id.action_save ->
                        saveAnnonce(holder.saveBtn,position)
                    R.id.action_details->
                        showDetails(position)
                }
                true
            }
            popupMenu.show()
        }
    }

    internal fun setAds(ads: List<RealEstateAd>) {
        this.ads = ads
        notifyDataSetChanged()
    }

    override fun getItemCount() = ads.size



    //helpers methods
    private fun addFragment(fragment: Fragment) {

        (adapterContext as MainActivity)!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun saveAnnonce(saveBtn : ImageButton,position: Int) {
        val wordsDao = RealEstateAdRoomDatabase.getDatabase((adapterContext as MainActivity), GlobalScope).realEstateDao()
        if(ads[position].state == 1){
            GlobalScope.launch {
                ads[position].state = -1
                wordsDao.delete(ads[position])
            }
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp)
        }else{
            GlobalScope.launch {
                ads[position].state = 1
                wordsDao.insert(ads[position])
            }
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_black_24dp)
        }
    }

    private fun showDetails(position: Int){
        var detailsFragment = AnnonceDetailsFragment()
        detailsFragment.idAd = position
        addFragment(detailsFragment)
    }


}