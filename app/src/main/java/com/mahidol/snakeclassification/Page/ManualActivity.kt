package com.mahidol.snakeclassification.Page

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.Adapter.ManualAdapter
import com.mahidol.snakeclassification.Model.ManualModel
import com.mahidol.snakeclassification.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_manual.*
import kotlinx.android.synthetic.main.activity_manual.manualBtn
import kotlinx.android.synthetic.main.manual_item.*

class ManualActivity : AppCompatActivity() {

    private lateinit var adapter: ManualAdapter
    private val models = ArrayList<ManualModel>()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        addDataToManualModel()
        setViewPagerChangeListener()
        setManualButtonClickListener()
    }

    private fun setManualButtonClickListener() {
        manualBtn.setOnClickListener {
            if (manualViewPager.currentItem < adapter.count - 1) {
                manualViewPager.currentItem = manualViewPager.currentItem + 1
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                onChooseFile()
                finish()
            }
        }
    }

    private fun onChooseFile() {
        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            var result : CropImage.ActivityResult? = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK){
                changeToLoadingPage(result!!)
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                var e: Exception = result!!.error
                Toast.makeText(this,"Error is: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun changeToLoadingPage(result :CropImage.ActivityResult?){
        val intent = Intent(this, LoadingActivity::class.java)
        intent.putExtra("imageUri", result!!.uri.toString())
        startActivity(intent)
    }

    private fun setViewPagerChangeListener() {

        adapter = ManualAdapter(models, this)
        manualViewPager.adapter = adapter

        manualViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                when (position) {
                    (adapter.count - 3) -> {
                        manualBtn.text = "NEXT"
                        manualScroll.setImageResource(R.mipmap.manual_scroll_step1)
                        setLanguage(position)
                    }
                    (adapter.count - 2) -> {
                        manualBtn.text = "NEXT"
                        manualScroll.setImageResource(R.mipmap.manual_scroll_step2)
                        setLanguage(position)
                    }
                    (adapter.count - 1) -> {
                        manualBtn.text = "START"
                        manualScroll.setImageResource(R.mipmap.manual_scroll_step3)
                        setLanguage(position)
                    }
                }
            }
        })
    }

    private fun addDataToManualModel() {
        models.add(
            ManualModel(
                R.mipmap.manual_pic_step1,
                getString(R.string.Manual_Name_Step1),
                getString(R.string.Manual_Info_Step1)
            )
        )
        models.add(
            ManualModel(
                R.mipmap.manual_pic_step2,
                getString(R.string.Manual_Name_Step2),
                getString(R.string.Manual_Info_Step2)
            )
        )
        models.add(
            ManualModel(
                R.mipmap.manual_pic_step3,
                getString(R.string.Manual_Name_Step3),
                getString(R.string.Manual_Info_Step3)
            )
        )
    }

    private fun setLanguage(page: Int) {
        val currentLanguage = LocaleHelper()
            .getPersistedData(this@ManualActivity, "en")
        if (currentLanguage == "th") {
            setLocaleLanguage("th", page)
        } else {
            setLocaleLanguage("en", page)
        }
    }

    private fun setLocaleLanguage(language: String, page: Int) {
        val context = LocaleHelper()
            .setLocal(this@ManualActivity, language)
        val resources = context.resources

        when (page) {
            0 -> {
                titleManual.text = resources.getString(R.string.Manual_Name_Step1)
                infoManual.text = resources.getString(R.string.Manual_Info_Step1)
            }
            1 -> {
                titleManual.text = resources.getString(R.string.Manual_Name_Step2)
                infoManual.text = resources.getString(R.string.Manual_Info_Step2)
            }
            else -> {
                titleManual.text = resources.getString(R.string.Manual_Name_Step3)
                infoManual.text = resources.getString(R.string.Manual_Info_Step3)
            }
        }

    }

}
