package com.mahidol.snakeclassification.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mahidol.snakeclassification.Model.ManualModel
import com.mahidol.snakeclassification.R
import kotlinx.android.synthetic.main.manual_item.view.*

class ManualAdapter(var models: ArrayList<ManualModel>, var context: Context) : PagerAdapter() {

    override fun isViewFromObject(view: View, objects: Any): Boolean {
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