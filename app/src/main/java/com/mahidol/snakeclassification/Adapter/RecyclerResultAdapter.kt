package com.mahidol.snakeclassification.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mahidol.snakeclassification.R
import com.mahidol.snakeclassification.Model.ResultData

class RecyclerResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var speciesTxt: TextView
    var serumTxt: TextView
    var probabilityPercent: TextView
    var colorResult: CardView
    var line:View

    init {
        speciesTxt = itemView.findViewById(R.id.speciesTxt)
        serumTxt = itemView.findViewById(R.id.serumTxt)
        probabilityPercent = itemView.findViewById(R.id.probabilityPercent)
        colorResult = itemView.findViewById(R.id.colorResult)
        line = itemView.findViewById(R.id.line)
    }


}

class RecyclerResultAdapter(
    private val resultData: ArrayList<ResultData>,
    private val mContext: Context
) :
    RecyclerView.Adapter<RecyclerResultViewHolder>() {

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerResultViewHolder {

        val itemView = inflater.inflate(R.layout.recycler_result, parent, false)
        return RecyclerResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerResultViewHolder, position: Int) {

        holder.speciesTxt.text = resultData[position].species
        holder.serumTxt.text = "เซรุ่ม : "
        holder.probabilityPercent.text = "${resultData[position].probability}%"
        var color = 0
        when(position%4){
            0 -> color = Color.parseColor("#E07A5F")
            1 -> color = Color.parseColor("#F2CC8F")
            2 -> color = Color.parseColor("#81B29A")
            3 -> {
                color = Color.parseColor("#E9E9E9")
                holder.line.visibility = View.GONE
                holder.serumTxt.visibility = View.GONE
            }
        }
        holder.colorResult.setCardBackgroundColor(color)

    }


    override fun getItemCount(): Int {
        return resultData.size
    }

}