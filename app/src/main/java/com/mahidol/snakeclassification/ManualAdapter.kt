package com.mahidol.snakeclassification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_manual.*
import kotlinx.android.synthetic.main.manual_item.*
import kotlinx.android.synthetic.main.manual_item.view.*

class ManualAdapter(var models: ArrayList<ManualModel>, var context: Context) : PagerAdapter() {

    override fun isViewFromObject(view: View, `objects`: Any): Boolean {
        return view == objects
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val view: View
        view = layoutInflater.inflate(R.layout.manual_item, container, false)

        view.picManual.setImageResource(models[position].image)
        view.titleManual.text = models[position].title
        view.infoManual.text = models[position].info
        container.addView(view, 0)


        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(container)
    }


}