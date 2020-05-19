package com.mahidol.snakeclassification.Dialog

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.mahidol.snakeclassification.Page.MainActivity
import com.mahidol.snakeclassification.R
import kotlinx.android.synthetic.main.popup_open_internet.*

class PopupOpenInternet(context: Context) : DialogFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_open_internet,container,false)
    }

    override fun onStart() {
        super.onStart()
        setInitDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_close_open_internet.setOnClickListener {
            dialog!!.dismiss()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setInitDialog(){
        val window = dialog!!.window
        // set ขนาดของ dialog
        setSizeDialog(window!!)
        // set ตำแหน่งของ dialog ให้อยู่ตรงกลาง
        setPositionCenterDialog(window)
        // set background ของ dialog
        setBackgroundDialog(window)
        // set ถ้ากดนอก dialog จะไม่เกิดอะไรขึ้น
        setCanceledOnTouchOutside(false)
    }

    private fun setSizeDialog(window: Window){
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        val width = size.x
        window.setLayout((width * 0.85).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setPositionCenterDialog(window : Window){
        window.setGravity(Gravity.CENTER)
    }

    private fun setBackgroundDialog(window : Window){
        window.setBackgroundDrawableResource(R.drawable.bg_popup)
    }

    private fun setCanceledOnTouchOutside( set : Boolean){
        dialog!!.setCanceledOnTouchOutside(set)
    }
}