package com.mahidol.snakeclassification.Page

import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.mahidol.snakeclassification.R
import com.mahidol.snakeclassification.Adapter.RecyclerResultAdapter
import com.mahidol.snakeclassification.Database.HistoryDataSource
import com.mahidol.snakeclassification.Helper.LocaleHelper
import com.mahidol.snakeclassification.Model.Detail
import com.mahidol.snakeclassification.Model.ResultData
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {
    var data_text: ArrayList<ResultData> = arrayListOf()
    private var dataSource: HistoryDataSource? = null
    lateinit var dataset: PieDataSet
    lateinit var data: PieData
    var dataResult: ArrayList<ResultData> = arrayListOf()
    var dataDetail = Detail()
    val colors = arrayListOf(
        Color.parseColor("#E07A5F"),
        Color.parseColor("#F2CC8F"), Color.parseColor("#81B29A"),
        Color.parseColor("#E9E9E9")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setupSQLite()
        receiveData()
        setLanguage()
        setUpDataSet()
        setUpPieChart()
        setUpRecyclerView()
    }

    private fun setupSQLite(){
        dataSource = HistoryDataSource(this)
        dataSource!!.open()
    }

    private fun setData(detail: Detail){
        dataDetail.rank1 = detail.rank1
        dataDetail.rank2 = detail.rank2
        dataDetail.rank3 = detail.rank3
        dataDetail.value1 = detail.value1
        dataDetail.value2 = detail.value2
        dataDetail.value3 = detail.value3
    }

    private fun receiveData() {
        val bundel = intent.extras
        var result = 0.toLong()

        if (bundel != null) {
            result = bundel.getLong("index")
            //textView6.text = result.toString()
            val data = dataSource!!.queryDetail(result)
            setData(data)
            println(data.rank1)
        } else {
            Log.d("Intent", "no data")
        }
    }

    private fun setUpPieChart() {
        data = PieData(dataset)
        pie_chart.animateY(1500)
        pie_chart.data = data
        pie_chart.holeRadius = 85F
        pie_chart.transparentCircleRadius = 85F
        pie_chart.centerText = "Top\n3"
        pie_chart.description.isEnabled = false
        pie_chart.setDrawEntryLabels(false)//species
        pie_chart.setHoleColor(Color.parseColor("#3D405B"))
        pie_chart.setEntryLabelTextSize(12f)
        pie_chart.setUsePercentValues(true)
        pie_chart.setCenterTextColor(Color.WHITE)
        pie_chart.setCenterTextSize(30f)
        pie_chart.highlightValues(null)
        pie_chart.legend.isEnabled = false
    }

    private fun setUpDataSet() {
        val entries = ArrayList<PieEntry>()
        for (data in data_text) {
            entries.add(PieEntry(data.probability, data.species))
        }
        dataset = PieDataSet(entries, "")
        dataset.valueTextColor = Color.WHITE
        dataset.valueFormatter = PercentFormatter()
        dataset.color = Color.BLUE
        dataset.selectionShift = 10f
        dataset.valueTextSize = 14f
        dataset.colors = colors
        dataset.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataset.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataset.valueLinePart1Length = 0.1f
        dataset.valueLinePart2Length = 0.3f
        dataset.valueLineColor = Color.WHITE

    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            LinearLayoutManager.VERTICAL, false
        )
        recycler_result_view.layoutManager = linearLayoutManager
        val adapter = RecyclerResultAdapter(data_text, this)
        recycler_result_view.adapter = adapter
    }

    private fun setWord(type :String,resources: Resources):String{
        when(type){
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

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

    private fun setTypeSerum(type :String,resources: Resources):String{
        when(type){
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

    private fun setResultDataSnake(resources: Resources) {

        data_text = arrayListOf()
        val otherValue = 100 - dataDetail.value1.toFloat() - dataDetail.value2.toFloat() - dataDetail.value3.toFloat()
        data_text.add(ResultData(setWord(dataDetail.rank1,resources),dataDetail.value1.toFloat(),setTypeSerum(dataDetail.rank1,resources)))
        data_text.add(ResultData(setWord(dataDetail.rank2,resources),dataDetail.value2.toFloat(),setTypeSerum(dataDetail.rank2,resources)))
        data_text.add(ResultData(setWord(dataDetail.rank3,resources),dataDetail.value3.toFloat(),setTypeSerum(dataDetail.rank3,resources)))
        data_text.add(ResultData(setWord("อื่นๆ",resources),otherValue.toDouble().round().toFloat(),null))
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
        setResultDataSnake(resources)
    }

}
