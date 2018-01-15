package com.doodle.doodle.Settings

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.doodle.doodle.R
import com.doodle.doodle.Splash.SplashActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    private var shared: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        logout.setOnClickListener {
            shared=getSharedPreferences("pref", Context.MODE_PRIVATE)
            editor=shared!!.edit()
            editor!!.clear()
            editor!!.commit()
            Toast.makeText(applicationContext,"로그아웃",Toast.LENGTH_SHORT).show()
            var intent : Intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
    }
}
