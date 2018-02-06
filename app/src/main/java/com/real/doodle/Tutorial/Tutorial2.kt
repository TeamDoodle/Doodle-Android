package com.real.doodle.Tutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_tutorial2.*

class Tutorial2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial2)

        tuto2.setOnClickListener {
            startActivity(Intent(applicationContext,Tutorial3::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }

    }
}
