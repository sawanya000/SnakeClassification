package com.mahidol.snakeclassification.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.R
import com.mahidol.snakeclassification.Model.ResultData

class RecyclerDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var speciesTxtDetail: TextView
    var serumTxtDetail: TextView
    var probabilityPercentDetail: TextView
    var serumNameDetail: TextView
    var lineDetail: View

    init {
        speciesTxtDetail = itemView.findViewById(R.id.speciesTxtDetail)
        serumTxtDetail = itemView.findViewById(R.id.serumTxtDetail)
        probabilityPercentDetail = itemView.findViewById(R.id.probabilityPercentDetail)
        serumNameDetail = itemView.findViewById(R.id.serumNameDetail)
        lineDetail = itemView.findViewById(R.id.lineDetail)
    }
}

class RecyclerDetailAdapter(
    private val resultData: ArrayList<ResultData>,
    private val mContext: Context
) :
    RecyclerView.Adapter<RecyclerDetailViewHolder>() {

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerDetailViewHolder {

        val itemView = inflater.inflate(R.layout.recycler_detail, parent, false)
        return RecyclerDetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerDetailViewHolder, position: Int) {
        setLanguage(holder)
        holder.speciesTxtDetail.text = resultData[position].species
        holder.serumNameDetail.text = resultData[position].serum
        holder.probabilityPercentDetail.text = "${resultData[position].probability}%"

        when (position % 4) {
            2 -> holder.lineDetail.visibility = View.GONE
        }
    }

    private fun setLanguage(holder: RecyclerDetailViewHolder) {
        val currentLanguage = LocaleHelper()
            .getPersistedData(mContext, "en")
        if (currentLanguage == "th") {
            setLocaleLanguage("th", holder)
        } else {
            setLocaleLanguage("en", holder)
        }
    }

    private fun setLocaleLanguage(language: String, holder: RecyclerDetailViewHolder) {
        val context = LocaleHelper()
            .setLocal(mContext, language)
        val resources = context.resources
        holder.serumTxtDetail.text = resources.getString(R.string.Serum)

    }

    override fun getItemCount(): Int {
        return resultData.size
    }

}