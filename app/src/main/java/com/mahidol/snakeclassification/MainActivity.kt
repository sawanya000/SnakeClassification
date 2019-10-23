package com.mahidol.snakeclassification

import android.content.Context
import android.content.Intent

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton()
        setButtonSwitchLanguage()
        setButtonManual()
    }

    private fun setButtonManual(){
        manualBtn.setOnClickListener(){
            var intent = Intent(this,ManualActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setButton() {
        pictureBtn.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun setButtonSwitchLanguage() {
        val currentLanguage = LocaleHelper().getPersistedData(this, "en")
        btn_sw_lan.isChecked = currentLanguage == "th"
        btn_sw_lan.setOnCheckedChangeListener { compoundButton, isChecked ->
            /*
                true = th
                false = en
            */
            if (isChecked) {
                setLocaleLanguage("th")
            } else {
                setLocaleLanguage("en")
            }
        }
    }

    private fun setLocaleLanguage(language: String) {
        val context = LocaleHelper().setLocal(this, language)
        val resources = context.resources

        /* change this code follow your id text
        textView.text = resources.getString(R.string.Main_Info)
        textView5.text = resources.getString(R.string.Main_Manual)*/
    }
}
