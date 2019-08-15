package com.example.housesfinder.Adapters

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Fragments.AnnonceDetailsFragment
import com.example.housesfinder.Model.Annonce
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.card_annonce.view.*
import java.lang.Double
import java.util.ArrayList

class AnnonceAdapter: BaseAdapter {


    var context : Context?=null
    var listAnnonceLocal = ArrayList<Annonce>()
    private  var state = 1

    constructor(listCard : ArrayList<Annonce>, context : Context){
        this.listAnnonceLocal = listCard
        this.context = context

    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = listAnnonceLocal.get(position)
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.card_annonce,null)
        //layoutItem.cardTitle.text = item.type!!
        Log.i("path",item.image!!.get(0).toString())
        if(isNumeric(item.image!!.get(0).toString()))
            layoutItem.cardImage.setImageResource(item.image!!.get(0).toString().toInt())
        else
            layoutItem.cardImage.setImageURI(item.image!!.get(0))

        layoutItem.cardImage.setOnClickListener {
            showDialog(item.image!!.get(0))
        }
        layoutItem.book_btn.setOnClickListener {
            saveAnnonce(layoutItem)
        }

        layoutItem.cardWilaya.text = item.wilaya!!
        layoutItem.cardPrice.text = item.price.toString()!! + " DA"
        //layoutItem.CardArea.text = item.area.toString()!! + "m²"
        //layoutItem.date.text=item.date

        layoutItem.seeDetails.setOnClickListener(View.OnClickListener {
            var detailsFragment = AnnonceDetailsFragment()
            detailsFragment.position = position
            addFragment(detailsFragment)
        })
        return layoutItem
    }

    private fun saveAnnonce(layoutItem: View?) {
        if(state == 1){
            layoutItem!!.book_btn.setImageResource(R.drawable.ic_bookmark_black_24dp)
            this.state = -1
        }else{
            layoutItem!!.book_btn.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
            this.state = 1
        }
    }

    override fun getItem(position: Int): Any {
        if (listAnnonceLocal!=null)
        { return listAnnonceLocal.get(position)}
        else
        {
            return listAnnonceLocal.get(position)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        if (listAnnonceLocal!=null)
        { return listAnnonceLocal.size}
        else
        {
            return listAnnonceLocal.size
        }
    }

    fun isNumeric(uri:String) :Boolean{
        var numeric = true

        try {
            val num = Double.parseDouble(uri)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
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

    private fun showDialog(image: Uri) {
        var layoutInflater = LayoutInflater.from(context)
        var dialogs = Dialog(context)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(true)
        val view = layoutInflater!!.inflate(R.layout.dialog_custom_layout,null)
        val photoViewd = view.findViewById<ImageView>(R.id.zoomedPhoto)

        // SET THE IMAGEVIEW DIMENSIONS
        val dimens = (context as MainActivity).windowManager.defaultDisplay.width
        val finalDimens = dimens

        val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
        photoViewd.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
        photoViewd.setScaleType(ImageView.ScaleType.CENTER_CROP)

        if(!isNumeric(image.toString()))
            photoViewd.setImageURI(image)
        else
            photoViewd.setImageResource(image.toString().toInt())
        dialogs.setContentView(view)
        /* noBtn.setOnClickListener { dialogs.dismiss() }*/

        dialogs.show()

    }
}
