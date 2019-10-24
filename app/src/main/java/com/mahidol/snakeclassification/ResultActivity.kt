package com.mahidol.snakeclassification

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_snake_info.*

class ResultActivity : AppCompatActivity() {
    var data_text:ArrayList<ResultData>
    lateinit var dataset:PieDataSet
    lateinit var data:PieData
    val colors = arrayListOf(Color.parseColor("#E07A5F"),
        Color.parseColor("#F2CC8F"),Color.parseColor("#81B29A"),
        Color.parseColor("#E9E9E9"))
    init {
        data_text = arrayListOf(ResultData("งูจงอาง",59F),
            ResultData("งูทับสมิงคลา",23F),ResultData("งูเห่า",12F),
            ResultData("อื่นๆ",6F)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpDataSet()
        setUpPieChart()
        setUpRecyclerView()
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

    private fun setUpRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            LinearLayoutManager.VERTICAL, false
        )
        recycler_result_view.layoutManager = linearLayoutManager
        val adapter = RecyclerResultAdapter(data_text, this)
        recycler_result_view.adapter = adapter
    }
}
