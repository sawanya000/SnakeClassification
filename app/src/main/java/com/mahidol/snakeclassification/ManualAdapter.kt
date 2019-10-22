package com.mahidol.snakeclassification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.manual_item.view.*

class ManualAdapter(var models:ArrayList<ManualModel>,var context:Context): PagerAdapter() {
    override fun isViewFromObject(view: View, `objects`: Any): Boolean {
         return view.equals(objects)
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var context:Context
        var layoutInflater = LayoutInflater.from(container.context)
        var view:View
        view = layoutInflater.inflate(R.layout.manual_item,container,false)

        view.picManual.setImageResource(models.get(position).getImage())
        view.titleManual.setText(models.get(position).getTitle())

        container.addView(view,0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(container)
    }
}