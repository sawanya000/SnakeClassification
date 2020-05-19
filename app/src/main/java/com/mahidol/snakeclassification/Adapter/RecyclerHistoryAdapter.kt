package com.mahidol.snakeclassification.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahidol.snakeclassification.Interface.ItemClickListener
import com.mahidol.snakeclassification.Model.History
import com.mahidol.snakeclassification.Page.DetailActivity
import com.mahidol.snakeclassification.R

class RecyclerHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    private var itemClickListener: ItemClickListener? = null
    var speciesTxt: TextView
    var timestampTxt: TextView
    var snakeImg: ImageView
    var layout: ConstraintLayout

    init {
        speciesTxt = itemView.findViewById(R.id.species_snake_hist)
        timestampTxt = itemView.findViewById(R.id.timestamp_hist)
        snakeImg = itemView.findViewById(R.id.image_snake_hist)
        layout = itemView.findViewById(R.id.layout_history)
        layout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
}

class RecyclerHistoryAdapter(
    private val resultData: ArrayList<History>,
    private val mContext: Context
) :
    RecyclerView.Adapter<RecyclerHistoryViewHolder>() {

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHistoryViewHolder {

        val itemView = inflater.inflate(R.layout.recycler_history, parent, false)
        return RecyclerHistoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerHistoryViewHolder, position: Int) {
        var item_size = (resultData.size - position) - 1
        holder.speciesTxt.text = "${setWord(resultData[item_size].species)} (${resultData[item_size].species})"
        holder.timestampTxt.text = resultData[item_size].timestamp
//        Glide.with(mContext)
//            .load(Uri.parse(resultData[item_size].image))
//            .apply(RequestOptions.circleCropTransform())
//            .into(holder.snakeImg)
        holder.snakeImg.setImageBitmap(StringToBitmap(resultData[item_size].image))
        setOnClickLayout(holder.layout,resultData[item_size].id)
    }

    fun StringToBitmap(string: String): Bitmap {
        val imageBytes = android.util.Base64.decode(string, 0)
        val image= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }

    private fun setWord(type :String):String{
        when(type){
            "King Cobra" -> return "ูจงอาง"
            "Cobra" -> return "งูเห่า"
            "Banded Krait" -> return "งูสามเหลี่ยม"
            "Malayan Krait" -> return "งูทับสมิงคลา"
            "Russell Viper" -> return "งูแมวเซา"
            "Malayan Pitviper" -> return "งูกะปะ"
            "White lipped Pitviper" -> return "งูเขียวหางไหม้ท้องเหลือง"
        }
        return ""
    }

    override fun getItemCount(): Int {
        return resultData.size
    }

    private fun setOnClickLayout(layoutTxt: ConstraintLayout,position: Long) {
        layoutTxt.setOnClickListener {
            openDetailPage(position)
        }
    }

    private fun openDetailPage(position: Long) {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.putExtra("index",position)
        mContext.startActivity(intent)
    }

}