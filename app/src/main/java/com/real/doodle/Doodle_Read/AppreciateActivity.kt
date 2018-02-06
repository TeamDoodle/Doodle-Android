package com.real.doodle.Doodle_Read

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.real.doodle.Doodle_Search.SearchActivity
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_appreciate.*

class AppreciateActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appreciate)
        appreciate_back.setOnClickListener { finish() }

        appreciate_search.setOnClickListener{
            startActivity(Intent(applicationContext,SearchActivity::class.java))
        }
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
