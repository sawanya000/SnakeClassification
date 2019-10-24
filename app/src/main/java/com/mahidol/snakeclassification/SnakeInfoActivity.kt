package com.mahidol.snakeclassification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_snake_info.*

class SnakeInfoActivity : AppCompatActivity() {
    private lateinit var data: ArrayList<InfoData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_info)
        setInfoDataSnake()
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

    private fun setInfoDataSnake() {
        data = arrayListOf(
            InfoData(
                R.mipmap.king_cobra_img,
                getStringFromRes(R.string.King_Cobra),
                getStringFromRes(R.string.Info_King_Cobra)
            ),
            InfoData(
                R.mipmap.cobra_img,
                getStringFromRes(R.string.Cobra),
                getStringFromRes(R.string.Info_Cobra)
            ),
            InfoData(
                R.mipmap.banded_krait_img,
                getStringFromRes(R.string.Banded_Krait),
                getStringFromRes(R.string.Info_Banded_Krait)
            ),
            InfoData(
                R.mipmap.malayan_krait_img,
                getStringFromRes(R.string.Malayan_Krait),
                getStringFromRes(R.string.Info_Malayan_Krait)
            ),
            InfoData(
                R.mipmap.russell_viper_img,
                getStringFromRes(R.string.Russell_Viper),
                getStringFromRes(R.string.Info_Russell_Viper)
            ),
            InfoData(
                R.mipmap.malayan_pit_viper_img,
                getStringFromRes(R.string.Malayan_Pitviper),
                getStringFromRes(R.string.Info_Malayan_Pitviper)
            ),
            InfoData(
                R.mipmap.white_lipped_pit_viper_img,
                getStringFromRes(R.string.White_lipped_Pitviper),
                getStringFromRes(R.string.Info_White_lipped_Pitviper)
            )

        )
    }

    private fun getStringFromRes(string: Int): String {
        return resources.getString(string)
    }
}
