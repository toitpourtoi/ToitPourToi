package com.example.housesfinder.Adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.custom_photo_with_delete_btn.view.*

class PhotoWithDeleteBtnAdapter : BaseAdapter {
    lateinit var uriList : ArrayList<Uri>
    lateinit var context: Context

    constructor(context: Context, uriList:ArrayList<Uri>){
        this.context = context
        this.uriList = uriList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val photo = uriList.get(position)
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.custom_photo_with_delete_btn,null)
        Log.i("PHOTO : ","here photo position ${position}")
        layoutItem.picked_photo.setImageURI(photo)
        layoutItem.delete_picked_media.setOnClickListener {
            uriList.removeAt(position)
            notifyDataSetChanged()
        }
        return  layoutItem
    }

    override fun getItem(position: Int): Any {
        return uriList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.uriList.size
    }
}