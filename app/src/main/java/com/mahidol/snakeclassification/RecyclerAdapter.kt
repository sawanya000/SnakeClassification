package com.mahidol.snakeclassification

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mahidol.snakeclassification.Interface.ItemClickListener


class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var headerTxt: TextView
    var introTxt: TextView
    var image_left: ImageView
    var image_right: ImageView
    var layoutTxt: ConstraintLayout
    var backgroundCard: ConstraintLayout
    private var itemClickListener: ItemClickListener? = null


    init {
        image_left = itemView.findViewById(R.id.snakeImgLeft)
        image_right = itemView.findViewById(R.id.snakeImgRight)
        backgroundCard = itemView.findViewById(R.id.backgroundCard)
        layoutTxt = itemView.findViewById(R.id.layout_text)
        headerTxt = itemView.findViewById(R.id.headerTxt)
        introTxt = itemView.findViewById(R.id.introTxt)
        introTxt.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
}

class RecyclerAdapter(private val infoData: ArrayList<InfoData>, private val mContext: Context) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    private val inflater: LayoutInflater
    private val marginLayout: Int

    init {
        inflater = LayoutInflater.from(mContext)
        marginLayout = 60
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val itemView = inflater.inflate(R.layout.recycler_card, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val params_layoutTxt = holder.layoutTxt.layoutParams as ConstraintLayout.LayoutParams
        holder.headerTxt.text = infoData[position].header
        holder.introTxt.text = infoData[position].intro

        when (position % 2) {
            0 -> {

                setImageInCard(holder.image_left, infoData[position].image)
                setVisibleDisappear(holder.image_right)
                setParamsToBgCard(holder.backgroundCard, "left")
                params_layoutTxt.leftToRight = holder.image_left.id
            }
            1 -> {
                setImageInCard(holder.image_right, infoData[position].image)
                setVisibleDisappear(holder.image_left)
                setParamsToBgCard(holder.backgroundCard, "right")
                params_layoutTxt.rightToLeft = holder.image_right.id
            }
        }

        setOnClickLayout(holder.introTxt)

    }

    private fun setParamsToBgCard(backgroundCard: ConstraintLayout, show: String) {
        val params_backgroundCard = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        if (show == "left")
            params_backgroundCard.setMargins(marginLayout, 0, 0, 0)
        else
            params_backgroundCard.setMargins(0, 0, marginLayout, 0)
        backgroundCard.layoutParams = params_backgroundCard
    }

    private fun setVisibleDisappear(view: View) {
        view.visibility = View.GONE
    }

    private fun setImageInCard(imageView: ImageView, image: Int) {
        imageView.setImageResource(image)
    }

    private fun setOnClickLayout(layoutTxt: TextView) {
        layoutTxt.setOnClickListener {
            openSaovabhaLink()
        }
    }

    private fun openSaovabhaLink() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.saovabha.com/th/snakefarm.asp"))
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(browserIntent)
    }

    override fun getItemCount(): Int {
        return infoData.size
    }

}