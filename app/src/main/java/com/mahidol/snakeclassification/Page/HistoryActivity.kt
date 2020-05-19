package com.mahidol.snakeclassification.Page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahidol.snakeclassification.Database.HistoryDataSource
import com.mahidol.snakeclassification.R
import com.mahidol.snakeclassification.Adapter.RecyclerHistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var dataSource: HistoryDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupSQLite()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            LinearLayoutManager.VERTICAL, false
        )
        recycler_history_view.layoutManager = linearLayoutManager
        val adapter =
            RecyclerHistoryAdapter(
                dataSource!!.allHistorys,
                this
            )
        recycler_history_view.adapter = adapter
    }

    private fun setupSQLite(){
        dataSource = HistoryDataSource(this)
        dataSource!!.open()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}
