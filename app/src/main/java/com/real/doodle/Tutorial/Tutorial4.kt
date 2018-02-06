package com.real.doodle.Tutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_tutorial4.*

class Tutorial4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial4)

        tuto4.setOnClickListener {
            startActivity(Intent(applicationContext,Tutorial5::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }
    }
}
