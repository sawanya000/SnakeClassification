package com.mahidol.snakeclassification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPictureButton()
        setInfoButton()
        setButtonSwitchLanguage()
        setManualButton()

    }

    private fun onChooseFile() {
        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            var result :CropImage.ActivityResult? = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK){
                /*
                    imageUri = result!!.uri
                    imageView.setImageURI(imageUri)
                */
                val intent = Intent(this,ResultActivity::class.java)
                startActivity(intent)
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                var e: Exception = result!!.error
                Toast.makeText(this,"Error is: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setInfoButton() {
        infoBtn.setOnClickListener {
            val intent = Intent(this, SnakeInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setManualButton() {
        manualBtn.setOnClickListener {
            val intent = Intent(this, ManualActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPictureButton() {
        pictureBtn.setOnClickListener {
            onChooseFile()

//            val intent = Intent(this, ResultActivity::class.java)
//            startActivity(intent)
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

    override fun onStart() {
        super.onStart()
        val currentLanguage = LocaleHelper().getPersistedData(this, "en")
        btn_sw_lan.isChecked = currentLanguage == "th"
    }

    private fun setLocaleLanguage(language: String) {
        val context = LocaleHelper().setLocal(this, language)
        val resources = context.resources

        textView2.text = resources.getString(R.string.Main_ChoosePic)
        textView3.text = resources.getString(R.string.Main_Info)
        textView4.text = resources.getString(R.string.Main_Manual)
        textView5.text = resources.getString(R.string.Main_History)
        /* change this code follow your id text
        textView.text = resources.getString(R.string.Main_Info)
        textView5.text = resources.getString(R.string.Main_Manual)*/
    }
}
