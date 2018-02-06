package com.real.doodle.Doodle_Write

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_before_write.*

class BeforeWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_write)

            val handler = Handler()
            handler.postDelayed(Runnable {
                startActivity(Intent(applicationContext,WriteActivity::class.java))
                finish()
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            }, 500)

    }
}
