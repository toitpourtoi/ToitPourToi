package com.example.housesfinder.Adapters

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Fragments.AnnonceDetailsFragment
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R

class RealEstateAdListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RealEstateAdListAdapter.AdViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ads = emptyList<RealEstateAd>() // Cached copy of words
    private val adapterContext = context
    private var state = -1
    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adItemView: TextView = itemView.findViewById(R.id.cardTitle)
        val wilayaItemView : TextView = itemView.findViewById(R.id.cardWilaya)
        val priceItemView : TextView = itemView.findViewById(R.id.cardPrice)
        val detailsBtn : ImageButton = itemView.findViewById(R.id.seeDetails)
        val imageView : ImageView = itemView.findViewById(R.id.cardImage)
        val saveBtn : ImageButton = itemView.findViewById(R.id.book_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemView = inflater.inflate(R.layout.card_annonce, parent, false)
        return AdViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val current = ads[position]
        holder.adItemView.text = current.category
        holder.wilayaItemView.text = current.wilaya
        holder.priceItemView.text = current.price
        holder.detailsBtn.setOnClickListener {
            var detailsFragment = AnnonceDetailsFragment()
            detailsFragment.idAd = position
            addFragment(detailsFragment)
        }
        //set the url of the image then set method zoom on click
        holder.imageView.setOnClickListener {
            showDialog(Uri.parse((R.drawable.home1).toString()))
        }
        //save annonce
        holder.saveBtn.setOnClickListener {
            saveAnnonce(holder.saveBtn)
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

    private fun showDialog(image: Uri) {
        var layoutInflater = LayoutInflater.from(adapterContext)
        var dialogs = Dialog(adapterContext)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(true)
        val view = layoutInflater!!.inflate(R.layout.dialog_custom_layout,null)
        val photoViewd = view.findViewById<ImageView>(R.id.zoomedPhoto)

        // SET THE IMAGEVIEW DIMENSIONS
        val dimens = (adapterContext as MainActivity).windowManager.defaultDisplay.width
        val finalDimens = dimens

        val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
        photoViewd.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
        photoViewd.setScaleType(ImageView.ScaleType.CENTER_CROP)


        //photoViewd.setImageURI(image)
        //this line will be deleted we,will nnot use hardcoded ads
        photoViewd.setImageResource(image.toString().toInt())
        dialogs.setContentView(view)
        /* noBtn.setOnClickListener { dialogs.dismiss() }*/

        dialogs.show()

    }

    private fun saveAnnonce(saveBtn : ImageButton) {
        if(state == 1){
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_black_24dp)
            this.state = -1
        }else{
            saveBtn.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp)
            this.state = 1
        }
    }
}