package com.example.housesfinder.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Adapters.PhotoWithDeleteBtnAdapter
import com.example.housesfinder.Model.Annonce
import com.example.housesfinder.R
import kotlinx.android.synthetic.main.fragment_add_annonce.*
import kotlinx.android.synthetic.main.fragment_register.*

class AddNewAnnonceFragment : Fragment() {
    private  val REQUEST_PICK_PHOTO = 1
    private var photos_number=0
    private val photos_max_number=6
    private  var  uriList :ArrayList<Uri> = ArrayList<Uri>()
    private lateinit var photoWithDeleteBtnAdapter: PhotoWithDeleteBtnAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoWithDeleteBtnAdapter = PhotoWithDeleteBtnAdapter(context!!,uriList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_add_annonce, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        add_photo_new_annonce.setOnClickListener {

            if ( photos_number==photos_max_number)
            {
                Toast.makeText(context,"Vous pouvez ajouter au maximum ${photos_max_number} photos", Toast.LENGTH_SHORT).show()
            }
            else{

                val intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO)}
        }


        submit_add_new_annonce.setOnClickListener {
            if(area_new_annonce.text.toString() == ""
                || wilaya_new_annonce.selectedItem.toString() == ""
                || price_new_annonce.text.toString() == ""
                || type_new_annonce.selectedItem.toString() == ""
                || photoWithDeleteBtnAdapter.uriList.isEmpty()
                || description_new_annonce.text.toString() == ""){
                Toast.makeText(context,"Veuillez introduire tout les champs", Toast.LENGTH_SHORT).show()
            }else {
                MainActivity.listAnnoce.add(Annonce(
                    area_new_annonce.text.toString().toDouble(),
                    wilaya_new_annonce.selectedItem.toString().drop(3),
                    price_new_annonce.text.toString().toDouble(),
                    type_new_annonce.selectedItem.toString(),
                    photoWithDeleteBtnAdapter.uriList,
                    MainActivity.mainSeller,
                    description_new_annonce.text.toString()
                ))
                //go to home fragment :
                addFragment(FragmentHome())
            }
        }
    }

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_PHOTO) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (data == null) {
                    // something is wrong
                    Toast.makeText(context,"Quelque chose est erroné", Toast.LENGTH_SHORT).show()
                }
                photos_number++
                val clipData = data!!.clipData
                if (clipData != null) { // handle multiple photo
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                    }
                } else { // handle single photo
                    val uri = data?.data
                    photoWithDeleteBtnAdapter.uriList.add(uri)
                    Log.i("ADDPHOTO","${photoWithDeleteBtnAdapter.uriList.size}")
                    photos_box_list.adapter = photoWithDeleteBtnAdapter
                }
            }
        }
    }
}