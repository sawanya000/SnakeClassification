package com.mahidol.snakeclassification

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_manual.*

class ManualActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        var models = ArrayList<ManualModel>()
        models.add(ManualModel(R.mipmap.manual_pic_step1,"title step1","info stpe1"))
        models.add(ManualModel(R.mipmap.manual_pic_step2,"title step2","info stpe2"))
        models.add(ManualModel(R.mipmap.manual_pic_step3,"title step3","info stpe3"))

        val adapter = ManualAdapter(models,this)
        manualViewPager.adapter = adapter

        manualViewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(position==(adapter.getCount()-1)){
                    manualBtn.setText("START")
                }
                else{
                    manualBtn.setText("NEXT")
                }
            }
        })

        manualBtn.setOnClickListener {
            if(manualViewPager.getCurrentItem()<adapter.getCount()-1){
                manualViewPager.setCurrentItem(manualViewPager.getCurrentItem() + 1);
            }
            else{

            }
        }
    }
}
