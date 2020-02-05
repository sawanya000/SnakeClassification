package com.mahidol.snakeclassification.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    var line: View
    var layout: ConstraintLayout

    init {
        speciesTxt = itemView.findViewById(R.id.species_snake_hist)
        timestampTxt = itemView.findViewById(R.id.timestamp_hist)
        snakeImg = itemView.findViewById(R.id.image_snake_hist)
        line = itemView.findViewById(R.id.line)
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

    override fun onBindViewHolder(holder: RecyclerHistoryViewHolder, position: Int) {

        holder.speciesTxt.text = resultData[position].species
        holder.timestampTxt.text = resultData[position].timestamp
        Glide.with(mContext)
            .load(Uri.parse(resultData[position].image))
            .into(holder.snakeImg)
        setOnClickLayout(holder.layout,position)
    }

    override fun getItemCount(): Int {
        return resultData.size
    }

    private fun setOnClickLayout(layoutTxt: ConstraintLayout,position: Int) {
        layoutTxt.setOnClickListener {
            openDetailPage(position)
        }
    }

    private fun openDetailPage(position: Int) {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.putExtra("index",position)
        mContext.startActivity(intent)
    }

}