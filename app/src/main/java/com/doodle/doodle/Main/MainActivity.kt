package com.doodle.doodle.Main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.doodle.doodle.Alarm.AlarmActivity
import com.doodle.doodle.Doodle_Books.BookActivity
import com.doodle.doodle.Doodle_Me.MyfeedActivity
import com.doodle.doodle.Doodle_Write.WriteActivity
import com.doodle.doodle.R
import com.doodle.doodle.Settings.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        글 작성
        main_write.setOnClickListener { startActivity(Intent(applicationContext,WriteActivity::class.java)) }

//        감상 하기
        main_read.setOnClickListener{}

//        나의 글
        main_myfeed.setOnClickListener { startActivity(Intent(applicationContext,MyfeedActivity::class.java)) }

//        글적 서적
        main_books.setOnClickListener { startActivity(Intent(applicationContext,BookActivity::class.java)) }

//        알람
        main_alarm.setOnClickListener { startActivity(Intent(applicationContext,AlarmActivity::class.java)) }

//        설정
        main_setting.setOnClickListener { startActivity(Intent(applicationContext,SettingActivity::class.java)) }
    }
}