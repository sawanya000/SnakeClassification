package com.mahidol.snakeclassification.Page

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private var wifiManager: WifiManager? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWifiManager()
        setupFilebase()
        setPictureButton()
        setInfoButton()
        setHistoryButton()
        setButtonSwitchLanguage()
        setManualButton()

    }

    private fun setupFilebase(){
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }

    private fun onChooseFile() {
        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result :CropImage.ActivityResult? = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK){
                val mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, result!!.uri)
                val imgSmall = resizeBitmap(mBitmap, 300, 300)
                changeToLoadingPage(result,BitmapToString(imgSmall))
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                val e: Exception = result!!.error
                Toast.makeText(this,"Error is: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun BitmapToString(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return android.util.Base64.encodeToString(outputStream.toByteArray(), android.util.Base64.DEFAULT)
    }

    private fun resizeBitmap(bitmap: Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    private fun changeToLoadingPage(result :CropImage.ActivityResult?,imgBitmap: String){
        val intent = Intent(this, LoadingActivity::class.java)
        intent.putExtra("imageUri", result!!.uri.toString())
        intent.putExtra("imageBitmap", imgBitmap)
        startActivity(intent)
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

    private fun setHistoryButton(){
        historyBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPictureButton() {
        pictureBtn.setOnClickListener {
            onChooseFile()
        }
    }

    private fun setWifiManager() {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun checkConnectWifi(){
        if (!wifiManager!!.isWifiEnabled) {
            Toast.makeText(this, "WiFi is disabled ", Toast.LENGTH_LONG)
                .show()
        }else{
            Toast.makeText(this, "WiFi is enable ", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setButtonSwitchLanguage() {
        val currentLanguage = LocaleHelper()
            .getPersistedData(this, "en")
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
        val currentLanguage = LocaleHelper()
            .getPersistedData(this, "en")
        btn_sw_lan.isChecked = currentLanguage == "th"
    }

    private fun setLocaleLanguage(language: String) {
        val context = LocaleHelper()
            .setLocal(this, language)
        val resources = context.resources

        textView2.text = resources.getString(R.string.Main_ChoosePic)
        textView3.text = resources.getString(R.string.Main_Info)
        textView4.text = resources.getString(R.string.Main_Manual)
        textView5.text = resources.getString(R.string.Main_History)

    }


}
