package com.mahidol.snakeclassification

import android.content.Context

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonopenbottomsheet.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout,null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        setButtonSwitchLanguage()
    }

    private fun setButtonSwitchLanguage(){
        val currentLanguage = LocaleHelper().getPersistedData(this,"en")
        btn_sw_lan.isChecked = currentLanguage == "th"
        btn_sw_lan.setOnCheckedChangeListener { compoundButton, isChecked ->
            /*
                true = th
                false = en
            */
            if (isChecked){
                setLocaleLanguage("th")
            }else{
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
