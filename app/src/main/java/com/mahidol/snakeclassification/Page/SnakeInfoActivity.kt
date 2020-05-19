package com.mahidol.snakeclassification.Page

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahidol.snakeclassification.Model.InfoData
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.R
import com.mahidol.snakeclassification.Adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_snake_info.*

class SnakeInfoActivity : AppCompatActivity() {
    private lateinit var data: ArrayList<InfoData>

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper().onAttach(newBase!!, "en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_info)
        setLanguage()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            LinearLayoutManager.VERTICAL, false
        )
        recycler_view.layoutManager = linearLayoutManager
        val adapter = RecyclerAdapter(data, this)
        recycler_view.adapter = adapter
    }

    private fun setInfoDataSnake(resources:Resources) {
        data = arrayListOf(
            InfoData(
                R.mipmap.king_cobra_img,
                resources.getString(R.string.King_Cobra),
                resources.getString(R.string.Info_King_Cobra)
            ),
            InfoData(
                R.mipmap.cobra_img,
                resources.getString(R.string.Cobra),
                resources.getString(R.string.Info_Cobra)
            ),
            InfoData(
                R.mipmap.banded_krait_img,
                resources.getString(R.string.Banded_Krait),
                resources.getString(R.string.Info_Banded_Krait)
            ),
            InfoData(
                R.mipmap.malayan_krait_img,
                resources.getString(R.string.Malayan_Krait),
                resources.getString(R.string.Info_Malayan_Krait)
            ),
            InfoData(
                R.mipmap.russell_viper_img,
                resources.getString(R.string.Russell_Viper),
                resources.getString(R.string.Info_Russell_Viper)
            ),
            InfoData(
                R.mipmap.malayan_pit_viper_img,
                resources.getString(R.string.Malayan_Pitviper),
                resources.getString(R.string.Info_Malayan_Pitviper)
            ),
            InfoData(
                R.mipmap.white_lipped_pit_viper_img,
                resources.getString(R.string.White_lipped_Pitviper),
                resources.getString(R.string.Info_White_lipped_Pitviper)
            )

        )
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
        setInfoDataSnake(resources)
    }

}
