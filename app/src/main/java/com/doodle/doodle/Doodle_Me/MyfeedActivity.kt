package com.doodle.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed.*

class MyfeedActivity : AppCompatActivity() ,View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed)

        myfeed_gatherFeed.setOnClickListener(this)
        myfeed_scrapFeed.setOnClickListener(this)

        myfeed_viewPager.adapter=Myfeed_pageAdapter(supportFragmentManager)
        myfeed_viewPager.setCurrentItem(0)
    }

    override fun onClick(v: View?) {
        when(v){
//            나의 글 버튼
            myfeed_gatherFeed->{
                myfeed_viewPager.currentItem=0
                Log.d("슈바 나의 글","슈바")
               }
//            담은 글 버튼
            myfeed_scrapFeed->{
                myfeed_viewPager.currentItem=1
                Log.d("슈바 담은 글","슈바")
               }
        }

    }



}
