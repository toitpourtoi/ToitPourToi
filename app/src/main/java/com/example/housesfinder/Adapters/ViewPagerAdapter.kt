package com.example.housesfinder.Adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.housesfinder.Activities.MainActivity
import android.graphics.drawable.Drawable
import android.util.Log
import android.webkit.WebView
import java.io.InputStream
import java.net.URL
import com.example.housesfinder.R


class ViewPagerAdapter(private val context: Context, private val images: ArrayList<String>, private val density: Float) : PagerAdapter() {
    private var layoutInflater:LayoutInflater?=null
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater!!.inflate(R.layout.custom_slide_image,container,false) as ViewGroup
        var image = v.findViewById<WebView>(R.id.slideImage)
        image.loadUrl(images[position])

        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View

        vp.removeView(v)
    }


}