package com.doodle.doodle.Main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.doodle.doodle.Alarm.AlarmActivity
import com.doodle.doodle.Doodle_Books.BookActivity
import com.doodle.doodle.Doodle_Me.FeedActivity
import com.doodle.doodle.Doodle_Read.AppreciateActivity
import com.doodle.doodle.Doodle_Write.WriteActivity
import com.doodle.doodle.Doodle_Write.WriteSplashActivity
import com.doodle.doodle.R
import com.doodle.doodle.Settings.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        글 작성
        write_linear.setOnClickListener {
            write_linear.setBackgroundResource(R.drawable.m1on)
            startActivity(Intent(applicationContext,WriteSplashActivity::class.java))

        }

//        감상 하기
        read_linear.setOnClickListener{
            read_linear.setBackgroundResource(R.drawable.m2on)
            startActivity(Intent(applicationContext,AppreciateActivity::class.java))
        }

//        나의 글
        myfeed_linear.setOnClickListener {
            myfeed_linear.setBackgroundResource(R.drawable.m3on)
            startActivity(Intent(applicationContext, FeedActivity::class.java))
        }

//        글적 서적
        books_linear.setOnClickListener {
            books_linear.setBackgroundResource(R.drawable.m4on)
            startActivity(Intent(applicationContext,BookActivity::class.java))
        }

//        알람
        main_alarm.setOnClickListener { startActivity(Intent(applicationContext,AlarmActivity::class.java)) }

//        설정
        main_setting.setOnClickListener { startActivity(Intent(applicationContext,SettingActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        write_linear.setBackgroundResource(R.drawable.m1off)
        read_linear.setBackgroundResource(R.drawable.m2off)
        myfeed_linear.setBackgroundResource(R.drawable.m3off)
        books_linear.setBackgroundResource(R.drawable.m4off)
    }
}
