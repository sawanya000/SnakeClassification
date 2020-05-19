package com.mahidol.snakeclassification.Page

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahidol.snakeclassification.Adapter.RecyclerDetailAdapter
import com.mahidol.snakeclassification.Database.HistoryDataSource
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.Model.Detail
import com.mahidol.snakeclassification.Model.History
import com.mahidol.snakeclassification.Model.ResultData
import com.mahidol.snakeclassification.R
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    private var dataSource: HistoryDataSource? = null
    var detail_text: ArrayList<ResultData> = arrayListOf()
    lateinit var data: Detail
    lateinit var history: History
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupSQLite()

        receiveData()
    }

    private fun setupSQLite() {
        dataSource = HistoryDataSource(this)
        dataSource!!.open()
    }

    private fun setWord(type: String, resources: Resources): String {
        when (type) {
            "King Cobra" -> return resources.getString(R.string.King_Cobra)
            "Cobra" -> return resources.getString(R.string.Cobra)
            "Banded Krait" -> return resources.getString(R.string.Banded_Krait)
            "Malayan Krait" -> return resources.getString(R.string.Malayan_Krait)
            "Russell Viper" -> return resources.getString(R.string.Russell_Viper)
            "Malayan Pitviper" -> return resources.getString(R.string.Malayan_Pitviper)
            "White lipped Pitviper" -> return resources.getString(R.string.White_lipped_Pitviper)
            else -> return resources.getString(R.string.Other)
        }
        return ""
    }

    private fun setLanguage() {
        val currentLanguage = LocaleHelper()
            .getPersistedData(this, "en")
        if (currentLanguage == "th") {
            setLocaleLanguage("th")
        } else {
            setLocaleLanguage("en")
        }
    }

    private fun setLocaleLanguage(language: String) {
        val context = LocaleHelper()
            .setLocal(this, language)
        val resources = context.resources
        setDetailDataSnake(resources)
    }

    private fun setDetailDataSnake(resources: Resources) {

        detail_text = arrayListOf()
        detail_text.add(
            ResultData(
                setWord(data.rank1, resources),
                data.value1.toFloat(),
                setTypeSerum(data.rank1, resources)
            )
        )
        detail_text.add(
            ResultData(
                setWord(data.rank2, resources),
                data.value2.toFloat(),
                setTypeSerum(data.rank2, resources)
            )
        )
        detail_text.add(
            ResultData(
                setWord(data.rank3, resources),
                data.value3.toFloat(),
                setTypeSerum(data.rank3, resources)
            )
        )
    }

    private fun setTypeSerum(type: String, resources: Resources): String {
        when (type) {
            "King Cobra" -> return resources.getString(R.string.Serum_King_Cobra)
            "Cobra" -> return resources.getString(R.string.Serum_Cobra)
            "Banded Krait" -> return resources.getString(R.string.Serum_Banded_Krait)
            "Malayan Krait" -> return resources.getString(R.string.Serum_Malayan_Krait)
            "Russell Viper" -> return resources.getString(R.string.Serum_Russell_Viper)
            "Malayan Pitviper" -> return resources.getString(R.string.Serum_Malayan_Pitviper)
            "White lipped Pitviper" -> return resources.getString(R.string.Serum_White_lipped_Pitviper)
        }
        return ""
    }

    private fun receiveData() {
        val bundel = intent.extras
        var result = 0.toLong()

        if (bundel != null) {
            result = bundel.getLong("index")
            setValue(result)
            setLanguage()
            setUpRecyclerView()
            setImage()

        } else {
            Log.d("Intent", "no data")
        }
    }

    private fun setValue(index: Long) {
        data = dataSource!!.queryDetail(index)
        history = dataSource!!.queryHistory(index)
    }

    private fun setImage() {
//        val mRoundCornetTransform = RoundedCorners(convertDpToPixel(40.toFloat(), this))
//        Glide.with(this)
//            .load(Uri.parse(history.image))
//            .centerCrop()
//            .transform(mRoundCornetTransform)
//            .into(image_snake_detail)

        image_snake_detail.setImageBitmap(StringToBitmap(history.image))

    }

    fun StringToBitmap(string: String): Bitmap {
        val imageBytes = android.util.Base64.decode(string, 0)
        val image= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }

    fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            LinearLayoutManager.VERTICAL, false
        )
        recycler_detail_view.layoutManager = linearLayoutManager
        val adapter = RecyclerDetailAdapter(detail_text, this)
        recycler_detail_view.adapter = adapter
    }
}
