package com.mahidol.snakeclassification

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var data_text = arrayListOf(ResultData("งูจงอาง",59F),
            ResultData("งูทับสมิงคลา",23F),ResultData("งูเห่า",12F),
            ResultData("อื่นๆ",6F)
        )
        var entries = ArrayList<PieEntry>()
        for (data in data_text) {
            entries.add(PieEntry(data.probability, data.species))
        }
        var dataset = PieDataSet(entries, "")
        dataset.setValueTextColor(Color.WHITE);
        dataset.valueFormatter = PercentFormatter()
        dataset.color = Color.BLUE
        dataset.selectionShift = 10f
        dataset.valueTextSize = 14f

        var colors = arrayListOf(Color.parseColor("#E07A5F"),
            Color.parseColor("#F2CC8F"),Color.parseColor("#81B29A"),
            Color.parseColor("#E9E9E9"))
        dataset.setColors(colors)
        dataset.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataset.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        dataset.valueLinePart1Length = 0.2f
        dataset.valueLinePart2Length = 0.3f
        dataset.valueLineColor = Color.WHITE
        var data = PieData(dataset)
        pie_chart.data = data
        pie_chart.holeRadius = 80F
        pie_chart.transparentCircleRadius = 80F
        pie_chart.animateY(3000)
        pie_chart.centerText = "Top\n3"
        pie_chart.description.isEnabled = false
        pie_chart.setDrawEntryLabels(false)//species
        pie_chart.setEntryLabelColor(Color.RED)
        pie_chart.setHoleColor(Color.parseColor("#3D405B"))
        pie_chart.setTransparentCircleColor(Color.BLUE)
        //pie_chart.setEntryLabelTypeface(tfRegular);
        pie_chart.setEntryLabelTextSize(12f)
        pie_chart.setUsePercentValues(true)
        pie_chart.setCenterTextColor(Color.WHITE)
        pie_chart.setCenterTextSize(20f)
        pie_chart.highlightValues(null);
        pie_chart.getLegend().setEnabled(false)
    }
}
