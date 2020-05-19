package com.mahidol.snakeclassification.Page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mahidol.snakeclassification.R

class SplashScreenActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(4000)
            } catch (e: InterruptedException) {
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }).start()
    }

    public override fun onResume() {
        super.onResume()
        handler?.postDelayed(runnable, 3000)
    }

    public override fun onStop() {
        super.onStop()
        handler?.removeCallbacks(runnable)
    }
}
