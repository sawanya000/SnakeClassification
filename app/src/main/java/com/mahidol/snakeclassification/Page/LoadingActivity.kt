package com.mahidol.snakeclassification.Page

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.mahidol.snakeclassification.Database.HistoryDataSource
import com.mahidol.snakeclassification.Helper.HTTPHelper
import com.mahidol.snakeclassification.Model.Detail
import com.mahidol.snakeclassification.Model.History
import com.mahidol.snakeclassification.Model.Result
import com.mahidol.snakeclassification.Dialog.PopupOpenInternet
import com.mahidol.snakeclassification.R
import kotlinx.android.synthetic.main.activity_loading.*
import java.text.SimpleDateFormat
import java.util.*

class LoadingActivity : AppCompatActivity() {
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private var dataSource: HistoryDataSource? = null
    lateinit var results: Result
    lateinit var imgBitmap: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        setupSQLite()
        setLoadingGif()
        if (isNetworkAvailable(this)){
            setupFilebase()
            receiveData()
        }else{
            showOpenInternetPopup()
        }

    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            try {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    Log.i("update_statut", "Network is available : true")
                    return true
                }
            } catch (e: java.lang.Exception) {
                Log.i("update_statut", "" + e.message)
            }
        }
        Log.i("update_statut", "Network is available : FALSE ")
        return false
    }

    private fun setupSQLite(){
        dataSource = HistoryDataSource(this)
        dataSource!!.open()
    }

    private fun receiveData() {
        val bundel = intent.extras
        var result = ""

        if (bundel != null) {
            result = bundel.getString("imageUri")!!
            imgBitmap = bundel.getString("imageBitmap")!!
            uploadImageToFirebase(result.toUri())
        } else {
            Log.d("Intent", "no data")
        }
    }

    private fun setupFilebase() {
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }

    private fun getCurrentTime():String{
        val sdf  = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH)
        val date = Date()
        return sdf.format(date)
    }

    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

    private fun loadPredict(link:Uri){
        val asyncTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, String, String>() {

            override fun doInBackground(vararg p0: String?): String {
                val helper = HTTPHelper()
                return helper.getHTTPData("http://35.209.129.18/${link.toString()}")
            }



            @SuppressLint("SimpleDateFormat")
            override fun onPostExecute(result: String?) {
                results = Gson().fromJson(result, Result::class.java)
                val history = dataSource!!.createHistory(imgBitmap,results.species1,getCurrentTime())
                val newDetail = dataSource!!.createDetail(setDetailData(history))
                changePage(newDetail)
            }
        }
        asyncTask.execute()
    }

    private fun changePage(newDetail:Detail?){
        if (newDetail != null){
            val intent = Intent(this@LoadingActivity, ResultActivity::class.java)
            intent.putExtra("index",newDetail.id)
            startActivity(intent)
            this@LoadingActivity.finish()
        }
    }

    private fun setDetailData(history: History):Detail{
        val detail = Detail()
        detail.id = history.id
        detail.rank1 = results.species1
        detail.value1 = (results.result1*100).round(2).toString()
        detail.rank2 = results.species2
        detail.value2 = (results.result2*100).round(2).toString()
        detail.rank3 = results.species3
        detail.value3 = (results.result3*100).round(2).toString()
        return detail
    }

    private fun uploadImageToFirebase(uri: Uri) {

        val imgRef = storageReference!!.child("/" + UUID.randomUUID().toString())
        imgRef.putFile(uri)
            .addOnSuccessListener {

                imgRef.downloadUrl.addOnSuccessListener {
                    val image = it
                    println("link : $image")
                    loadPredict(image)
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "..... Failed .....", Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { takeSnapshot ->
                val process = 100.0 + takeSnapshot.bytesTransferred / takeSnapshot.totalByteCount
            }
    }

    private fun setLoadingGif() {
        val width = convertDpToPixel(100.toFloat(), this)
        val height = convertDpToPixel(16.toFloat(), this)
        Glide
            .with(this)
            .load(R.drawable.loading)
            .fitCenter()
            .override(width, height)
            .into(loading_gif)
    }

    private fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }

    private fun showOpenInternetPopup() {
        val fm = this.supportFragmentManager
        val schedule =
            PopupOpenInternet(this)
        schedule.show(fm, "Open Internet Dialog")
    }
}
