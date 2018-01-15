package com.doodle.doodle.Doodle_Write

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.doodle.doodle.Login.LoginActivity
import com.doodle.doodle.Main.MainActivity
import com.doodle.doodle.R

class WriteSplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_splash)

        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent=Intent(applicationContext,WriteActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }, 1000)
    }
}
