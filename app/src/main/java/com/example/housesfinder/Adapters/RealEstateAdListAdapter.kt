package com.example.housesfinder.Adapters


import android.app.AlertDialog
import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.housesfinder.Activities.MainActivity
import com.example.housesfinder.Fragments.AnnonceDetailsFragment
import com.example.housesfinder.Model.RealEstateAd
import com.example.housesfinder.R
import com.google.firebase.database.FirebaseDatabase


class RealEstateAdListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RealEstateAdListAdapter.AdViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ads = emptyList<RealEstateAd>() // Cached copy of words
    private val adapterContext = context

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val adItemView: TextView = itemView.findViewById(com.example.housesfinder.R.id.cardTitle)
        val wilayaItemView : TextView = itemView.findViewById(com.example.housesfinder.R.id.cardWilaya)
        val linkItemView : TextView = itemView.findViewById(com.example.housesfinder.R.id.linkDetails)
        val dateItemView : TextView = itemView.findViewById(com.example.housesfinder.R.id.dateDetails)
        val detailsBtn : ImageButton = itemView.findViewById(com.example.housesfinder.R.id.seeDetails)
        val saveBtn : ImageButton = itemView.findViewById(com.example.housesfinder.R.id.book_btn)
        val moreBtn : ImageButton = itemView.findViewById(com.example.housesfinder.R.id.more_btn)
        val shareBtn : ImageButton = itemView.findViewById(R.id.shareBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemView = inflater.inflate(com.example.housesfinder.R.layout.card_annonce, parent, false)
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
            holder.saveBtn.setBackgroundResource(com.example.housesfinder.R.drawable.ic_bookmark_black_24dp)
        }
        //set actions
        holder.detailsBtn.setOnClickListener {
            showDetails(position)
        }

        //share annonce
        holder.shareBtn.setOnClickListener{
            sendSMS("0551789142cd",ads[position].link)
        }

        //save annonce
        holder.saveBtn.setOnClickListener {
            saveAnnonce(holder.saveBtn,position)
        }
        //display menu when clicking on moreBtn
        holder.moreBtn.setOnClickListener {
            var popupMenu = PopupMenu(adapterContext,it)
            popupMenu.menuInflater.inflate(com.example.housesfinder.R.menu.popup_menu,popupMenu.menu)
            if(ads[position].state == 1){
                popupMenu.menu.getItem(0).setTitle("Supprimer")
            }
            popupMenu.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    com.example.housesfinder.R.id.action_save ->
                        saveAnnonce(holder.saveBtn,position)
                    com.example.housesfinder.R.id.action_details->
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
                com.example.housesfinder.R.anim.design_bottom_sheet_slide_in,
                com.example.housesfinder.R.anim.design_bottom_sheet_slide_out
            )
            .replace(com.example.housesfinder.R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
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
            saveBtn.setBackgroundResource(com.example.housesfinder.R.drawable.ic_bookmark_black_24dp)
            Toast.makeText(adapterContext, "L'annonce a bien été enregistrée", Toast.LENGTH_SHORT).show()
        }
        //https://www.algerimmo.com/achat-vente/achat-vente-maison/bungalow-zemmouri-el-bahri-2205.htm
        //TODO :refactor this code
        var  Ref: FirebaseDatabase = FirebaseDatabase.getInstance()
        var ref = Ref.reference.child("saved_posts").child("user_posts")
            .child(MainActivity.USER_ID).child("saved_posts").push()
        ref.child("key").setValue(ref.key)
        ads[position].key=ref.key!!
        ref.setValue( ads[position].link)

    }
    private fun deleteAnnonce(saveBtn: ImageButton,position: Int){
        val builder = AlertDialog.Builder(adapterContext)
        builder.setTitle("Supprimer l'annonce")
        builder.setMessage("Etes vous  sûre  de vouloir  supprimer cette annonce de la liste des enregistrements ?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Oui") { dialog, which ->
            Toast.makeText(adapterContext, "L'annonce a bien été supprimée", Toast.LENGTH_SHORT).show()
            ads[position].state = -1
            MainActivity.adViewModel.delete(ads[position])
            var  Ref: FirebaseDatabase = FirebaseDatabase.getInstance()
            var ref = Ref.reference.child("saved_posts").child("user_posts")
                .child(MainActivity.USER_ID).child("saved_posts").child(ads[position].key)
            ref.ref.removeValue()


            saveBtn.setBackgroundResource(com.example.housesfinder.R.drawable.ic_bookmark_border_black_24dp)
        }

        builder.setNegativeButton("Annuler") { dialog, which ->
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

    fun sendSMS(phoneNo: String, msg: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, msg, null, null)
            Toast.makeText(
                adapterContext, "Message Sent",
                Toast.LENGTH_LONG
            ).show()
        } catch (ex: Exception) {
            Toast.makeText(
                adapterContext, ex.message.toString(),
                Toast.LENGTH_LONG
            ).show()
            ex.printStackTrace()
        }

    }

}