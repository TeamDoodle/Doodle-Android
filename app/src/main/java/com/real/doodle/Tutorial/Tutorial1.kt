package com.real.doodle.Tutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_tutorial1.*

class Tutorial1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial1)

        tuto1.setOnClickListener {
            startActivity(Intent(applicationContext,Tutorial2::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }
    }
}
