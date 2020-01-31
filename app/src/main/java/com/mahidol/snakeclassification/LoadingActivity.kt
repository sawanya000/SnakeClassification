package com.mahidol.snakeclassification

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.popup_loading.*
import java.util.*

class LoadingActivity : AppCompatActivity() {
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        setupFilebase()
        receiveData()
        setLoadingGif()
    }

    private fun receiveData() {
        val bundel = intent.extras
        var result = ""

        if (bundel != null) {
            result = bundel.getString("imageUri")!!
            uploadImageToFirebase(result.toUri())
        } else {
            Log.d("Intent", "no data")
        }
    }

    private fun setupFilebase() {
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }

    private fun uploadImageToFirebase(uri: Uri) {

        Toast.makeText(applicationContext, "..... Uploading .....", Toast.LENGTH_SHORT).show()
        val imgRef = storageReference!!.child("/" + UUID.randomUUID().toString())
        imgRef.putFile(uri!!)
            .addOnSuccessListener {
                Toast.makeText(
                    applicationContext,
                    "..... File upload successfully .....",
                    Toast.LENGTH_SHORT
                ).show()
                val imageUri = it.uploadSessionUri

                imgRef.downloadUrl.addOnSuccessListener {
                    val image = it
                    println("link : $image")
                }
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)

            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "..... Failed .....", Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { takeSnapshot ->
                val process = 100.0 + takeSnapshot.bytesTransferred / takeSnapshot.totalByteCount
                Toast.makeText(
                    applicationContext, "..... Uploaded ${process.toInt()} % .....",
                    Toast.LENGTH_SHORT
                ).show()
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

    fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}
