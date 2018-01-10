package com.doodle.doodle.Doodle_Me

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Login.CommonData
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private var requestManager:RequestManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profile_back.setOnClickListener { startActivity(Intent(applicationContext,FeedActivity::class.java)) }
        requestManager=Glide.with(this)
        requestManager!!.load(CommonData.loginData!!.profile.profile!!)
                .into(profilePhoto)
    }
}
