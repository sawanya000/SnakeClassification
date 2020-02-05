package com.mahidol.snakeclassification.Page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.mahidol.snakeclassification.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        receiveData()
    }

    private fun receiveData() {
        val bundel = intent.extras
        var result = 0

        if (bundel != null) {
            result = bundel.getInt("index")
            textView6.text = result.toString()
        } else {
            Log.d("Intent", "no data")
        }
    }
}
