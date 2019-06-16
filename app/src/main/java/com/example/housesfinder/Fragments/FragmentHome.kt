package com.example.housesfinder.Fragments

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Model.Annonce
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.card_annonce.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Double
import java.util.*

class FragmentHome : Fragment() {


    var adapter: AnonceAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnonceAdapter(MainActivity.listAnnoce, this!!.activity!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        annoncesList.adapter = adapter

        annoncesList.isTextFilterEnabled=true

        btn_filter.setOnClickListener {
            showDialog()

        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Toast.makeText(activity,"here i am",Toast.LENGTH_SHORT)
                if (TextUtils.isEmpty(newText)) {
                    annoncesList.clearTextFilter();
                }
                else {
                    annoncesList.setFilterText(newText.toString());
                }

                return true;


            }


    })}
    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
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
    inner class AnonceAdapter: BaseAdapter , Filterable {


        var context : Context?=null
        var listAnnonceLocal = ArrayList<Annonce>()
        var filteredList = ArrayList<Annonce>()
        var listFilter : AnonceFilter? = null

        constructor(listCard : ArrayList<Annonce>, context : Context){
            this.listAnnonceLocal = listCard
            this.filteredList=listCard
            this.context = context

        }

        override fun getFilter(): Filter? {
            if (listFilter == null) {
                listFilter= AnonceFilter()
            }

            return listFilter

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val item = filteredList.get(position)
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

        override fun getItem(position: Int): Any {
            if (filteredList!=null)
            { return filteredList.get(position)}
            else
            {
                return listAnnonceLocal.get(position)
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            if (filteredList!=null)
            { return filteredList.size}
            else
            {
                return listAnnonceLocal.size
            }
        }

        fun sortListByWilaya()
        {
            Collections.sort(filteredList, object : Comparator<Annonce> {
                override fun compare(emp1: Annonce, emp2: Annonce): Int {
                    return emp1.wilaya!!.compareTo(emp2.wilaya!!)
                }

            })

            notifyDataSetChanged();
        }
        fun sortListByPrice()
        {
            Collections.sort(filteredList, object : Comparator<Annonce> {
                override fun compare(emp1: Annonce, emp2: Annonce): Int {

                    return Double.compare(emp1.price!!,emp2.price!!)

                }



            })

                    notifyDataSetChanged()
        }
        fun sortListByDate( )
        {
            Collections.sort(filteredList, object : Comparator<Annonce> {
                override fun compare(emp1: Annonce, emp2: Annonce): Int {
                    return emp1.date.compareTo(emp2.date)




                }

            })

            notifyDataSetChanged();
        }



        inner  class AnonceFilter : Filter() {

            @Override
            override fun  performFiltering(constraint:CharSequence):FilterResults {
                var  filterResults:FilterResults =  FilterResults ()
                if (constraint!=null && constraint.length>0) {

                    var  tempList: ArrayList<Annonce>  =  ArrayList<Annonce>();

                    // search content in friend list
                    for (annonce : Annonce in listAnnonceLocal ) {
                        if (annonce.wilaya!!.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            tempList.add(annonce)

                        }
                    }

                    filterResults.count = tempList.size
                    filterResults.values = tempList

                } else {
                    filterResults.count = listAnnonceLocal.size
                    filterResults.values = listAnnonceLocal
                }

                return filterResults;
            }

            /**
             * Notify about filtered list to ui
             * @param constraint text
             * @param results filtered result
             */
            @SuppressWarnings("unchecked")
            @Override
            override fun  publishResults(constraint:CharSequence, results: FilterResults) {
                filteredList = results.values  as ArrayList<Annonce>

                notifyDataSetChanged();
            }
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

    private fun showDialog() {
        var dialogs = Dialog(this.activity)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)

        dialogs.setContentView(R.layout.filter_alert)

        val yesBtn = dialogs.findViewById(R.id.btn_ok) as Button
        val filterSpinner = dialogs.findViewById(R.id.filtres) as Spinner





        yesBtn.setOnClickListener {
            if (filterSpinner.selectedItem.toString().equals("Wilaya"))
            {
                adapter!!.sortListByWilaya()
                dialogs.hide()

            }
            if (filterSpinner.selectedItem.toString().equals("Date"))
            {
                adapter!!.sortListByDate()
                dialogs.hide()

            }
            if (filterSpinner.selectedItem.toString().equals("Prix"))
            {   adapter!!.sortListByPrice()
                dialogs.hide()




            }



        }
         dialogs.show()

    }



}
