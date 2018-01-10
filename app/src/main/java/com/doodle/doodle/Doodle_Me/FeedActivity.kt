package com.doodle.doodle.Doodle_Me

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Read.SwipeViewPager
import com.doodle.doodle.Login.CommonData
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed.*

class FeedActivity : AppCompatActivity() ,View.OnClickListener {
    var requestManager:RequestManager?=null
    var profileImage:ImageView?=null
    var viewPager: SwipeViewPager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed)
        viewPager=findViewById(R.id.myfeed_viewPager)
        myfeed_gatherFeed.setOnClickListener(this)
        myfeed_scrapFeed.setOnClickListener(this)
        profileImage= findViewById(R.id.myfeed_profilePhoto)

        viewPager!!.adapter= FeedpageAdapter(supportFragmentManager)
        viewPager!!.setCurrentItem(0)
        requestManager=Glide.with(this)
        requestManager!!.load(CommonData.loginData!!.profile.profile).into(profileImage!!)
        myfeed_writingName.text=CommonData.loginData!!.profile.nickname
        myfeed_editProfile.setOnClickListener { startActivity(Intent(applicationContext,ProfileActivity::class.java)) }
    }

    override fun onClick(v: View?) {
        when(v){
//            나의 글 버튼
            myfeed_gatherFeed->{
                myfeed_gatherFeed.setImageResource(R.drawable.mymyon)
                myfeed_scrapFeed.setImageResource(R.drawable.scrapoff)
                myfeed_viewPager.currentItem=0

                Log.d("슈바 나의 글","슈바")

               }
//            담은 글 버튼
            myfeed_scrapFeed->{
                myfeed_gatherFeed.setImageResource(R.drawable.mymyoff)
                myfeed_scrapFeed.setImageResource(R.drawable.scrapon)
                myfeed_viewPager.currentItem=1
                Log.d("슈바 담은 글","슈바")

               }
        }

    }



}
