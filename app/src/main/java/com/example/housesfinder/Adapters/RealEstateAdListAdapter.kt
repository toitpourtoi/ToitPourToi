package com.example.housesfinder.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Database.RealEstateAdRoomDatabase
import com.example.housesfinder.Fragments.AnnonceDetailsFragment
import com.example.housesfinder.Fragments.FragmentHome
import com.example.housesfinder.Fragments.RegisterFragment
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
        Log.i("USERID",current.userId)
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
        if(ads[position].state == 1){
            deleteAnnonce(saveBtn,position)
        }else{
            ads[position].state = 1
            ads[position].userId = MainActivity.USER_ID
            MainActivity.adViewModel.insert(ads[position])
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_black_24dp)
            Toast.makeText(adapterContext, "L'annonce a bien été enregistrée", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteAnnonce(saveBtn: ImageButton,position: Int){
        val builder = AlertDialog.Builder(adapterContext)
        builder.setTitle("Supprimer l'annonce")
        builder.setMessage("Vous êtes sûre que vous vouliez supprimer cette annonce de liste des enregistrements ?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(adapterContext, "L'annonce a bien été supprimée", Toast.LENGTH_SHORT).show()
            ads[position].state = -1
            MainActivity.adViewModel.delete(ads[position])
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }
    private fun showDetails(position: Int){
        var detailsFragment = AnnonceDetailsFragment()
        detailsFragment.idAd = position
        detailsFragment.annonce = ads[position]
        addFragment(detailsFragment)
    }


}