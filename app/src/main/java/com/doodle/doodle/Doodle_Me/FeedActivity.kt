package com.doodle.doodle.Doodle_Me

import android.content.Context
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
import com.doodle.doodle.Main.MainActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() ,View.OnClickListener {
    var requestManager:RequestManager?=null
    var profileImage:ImageView?=null
    var viewPager: SwipeViewPager?=null
    var networkService:NetworkService?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed)
        viewPager=findViewById(R.id.myfeed_viewPager)
        myfeed_gatherFeed.setOnClickListener(this)
        myfeed_scrapFeed.setOnClickListener(this)
        profileImage= findViewById(R.id.myfeed_profilePhoto)

        networkService=ApplicationController.instance!!.networkService

        viewPager!!.adapter= FeedpageAdapter(supportFragmentManager)
        viewPager!!.setCurrentItem(0)
        requestManager=Glide.with(this)
        requestManager!!.load(CommonData.loginData!!.profile.profile).into(profileImage!!)
        myfeed_profileComment.setText(CommonData.loginData!!.profile.description)
        requestManager!!.load(CommonData.loginData!!.profile.profile!!) .into(myfeed_profilePhoto)



        myfeed_writingName.text=CommonData.loginData!!.profile.nickname
        myfeed_editProfile.setOnClickListener {
            startActivityForResult(Intent(applicationContext,ProfileActivity::class.java), 1) }

        val getProfileResponse=networkService!!.getProfile(getToken("token"))
        getProfileResponse.enqueue(object :Callback<GetProfileResponse>{
            override fun onResponse(call: Call<GetProfileResponse>?, response: Response<GetProfileResponse>?) {
                if(response!!.isSuccessful){
                    myfeed_postingNum.text=response.body().result!!.doodle_count.toString()
                    myfeed_scrapNum.text=response.body().result!!.scrap_count.toString()
                }
            }
            override fun onFailure(call: Call<GetProfileResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신상태를 확인해주세요")
            }
        })

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //var description : String = "\"" + data!!.getStringExtra("description") + "\""
        myfeed_profileComment.setText(data!!.getStringExtra("description"))

        when(data!!.getIntExtra("flag", 1)){
            1->{
                return
            }
            2->{

                requestManager=Glide.with(this)
                requestManager!!.load(data!!.getStringExtra("profileimage")) .into(myfeed_profilePhoto)
            }

        }

    }

    fun getToken(key : String) : String{
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }


}
