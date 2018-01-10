package com.doodle.doodle.Doodle_Read

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_appreciate.*
import kotlinx.android.synthetic.main.activity_write.*

class AppreciateActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appreciate)

        all.setOnClickListener(this)
        week.setOnClickListener(this)
        day.setOnClickListener(this)
        appreciate_viewPager.adapter = Appreciate_pageAdapter(supportFragmentManager)
        appreciate_viewPager.setCurrentItem(0)
        all.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo_extra_bold.ttf")
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when(v){    //뷰를 기준으로 구분
//            전체감상을 눌렀을 때
            all->{
                all.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo_extra_bold.ttf")
                week.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                day.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                appreciate_viewPager.currentItem = 0
            }
//            주간감성을 눌렀을 때

            week->{
                week.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo_extra_bold.ttf")
                all.typeface= Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                day.typeface= Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                appreciate_viewPager.currentItem = 1
            }
//            일일 감성을 눌렀을 때
            day->{
                week.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                all.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo.ttf")
                day.typeface=Typeface.createFromAsset(assets,"fonts/nanum_myeongjo_extra_bold.ttf")
                appreciate_viewPager.currentItem = 2
            }
        }
    }

}
