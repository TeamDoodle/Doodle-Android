package com.doodle.doodle.Doodle_Others

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.AppLaunchChecker
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_big_other.*
import kotlinx.android.synthetic.main.activity_other.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BigOtherActivity : AppCompatActivity() {
    private var BigOtherRecyclerView: RecyclerView? = null
    private var doodleList: ArrayList<userDoodleData>? = null
    private var doodleUserList:userData?=null
    private var bigOtherAdapter: BigOtherAdapter? = null
    private var user_idx:Int?= null

    private var requestManager: RequestManager? = null
    private var networkService: NetworkService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_other)

        user_idx = intent.getIntExtra("user_idx", 0)
        networkService=ApplicationController.instance!!.networkService

        doodleList = ArrayList<userDoodleData>()

        requestManager = Glide.with(this)
        big_back.setOnClickListener { startActivity(Intent(applicationContext,OtherActivity::class.java)) }
        bigOtherAdapter = BigOtherAdapter(this, doodleList!!, requestManager!!)
        BigOtherRecyclerView = findViewById(R.id.big_other_recyclerview)
        val layoutManager:LinearLayoutManager= LinearLayoutManager(this)
       BigOtherRecyclerView!!.layoutManager=layoutManager
        BigOtherRecyclerView!!.adapter = bigOtherAdapter
        getOtherFeed(user_idx!!)
    }

    fun getOtherFeed(user_index: Int) {
        Log.d("user_idx",user_index.toString())
        var otherResponse = networkService!!.getOthers(getToken("token"), user_index!!)
        otherResponse.enqueue(object : Callback<OtherResponse> {
            override fun onResponse(call: Call<OtherResponse>?, response: Response<OtherResponse>?) {
                if (response!!.isSuccessful) {

                    doodleUserList=response.body().result.user
                    if(response.body().result.user.profile==null){
                        requestManager!!.load(R.drawable.profile).into(big_other_profile)
                    }else{
                        requestManager!!.load(response.body().result.user.profile).into(big_other_profile)
                    }
                    big_other_nickname.text=response.body().result.user.nickname
                    doodleList = response.body().result.doodle
                    bigOtherAdapter = BigOtherAdapter(this@BigOtherActivity, doodleList!!, requestManager!!)
                    BigOtherRecyclerView!!.adapter = bigOtherAdapter
                    bigOtherAdapter!!.setAdapter(response.body().result.doodle)
                    bigOtherAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<OtherResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신상태를 확인해 주세요")
            }
        })
    }

    fun getToken(key: String): String {
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

}
