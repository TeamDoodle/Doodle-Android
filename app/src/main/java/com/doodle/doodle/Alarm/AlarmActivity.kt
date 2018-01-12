package com.doodle.doodle.Alarm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity() {

    private var alarmRecyclerViewNew: RecyclerView? = null
    private var alarmRecyclerViewOld: RecyclerView? = null
    private var linearLayoutManagerNew: LinearLayoutManager? = null
    private var linearLayoutManagerOld: LinearLayoutManager? = null
    private var alarmAdapterNew: AlarmAdapter? = null
    private var alarmAdapterOld: AlarmAdapter? = null
    private var networkService: NetworkService? = null
    private var alarmDataNew: ArrayList<AlarmData>? = null
    private var alarmDataOld: ArrayList<AlarmData>? = null
    private var requestManager: RequestManager? = null
    private var pref: SharedPreferences? = null
    private var token: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        pref = getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token","")

        alarmRecyclerViewNew = findViewById(R.id.alarm_recycler_new)
        alarmRecyclerViewOld = findViewById(R.id.alarm_recycler_old)

        alarmRecyclerViewNew!!.setHasFixedSize(true)
        alarmRecyclerViewOld!!.setHasFixedSize(true)
        networkService = ApplicationController.instance!!.networkService


        linearLayoutManagerNew = LinearLayoutManager(this)
        linearLayoutManagerNew!!.orientation = LinearLayoutManager.VERTICAL

        linearLayoutManagerOld = LinearLayoutManager(this)
        linearLayoutManagerOld!!.orientation = LinearLayoutManager.VERTICAL

        alarmRecyclerViewNew!!.layoutManager=linearLayoutManagerNew
        alarmRecyclerViewOld!!.layoutManager=linearLayoutManagerOld

        requestManager = Glide.with(this)

        alarmDataNew = ArrayList<AlarmData>()
        alarmDataOld = ArrayList<AlarmData>()

        alarmAdapterNew = AlarmAdapter(this, alarmDataNew, requestManager!!)
        alarmAdapterOld = AlarmAdapter(this, alarmDataOld, requestManager!!)

        alarmAdapterNew!!.setNetworkservice(networkService!!)
        alarmAdapterOld!!.setNetworkservice(networkService!!)
//        alarmAdapterOld!!.settineToken(token!!)

        alarmRecyclerViewNew!!.adapter = alarmAdapterNew
        alarmRecyclerViewOld!!.adapter = alarmAdapterOld

        networking()

    }

    fun networking() {
        var getAlarmList = networkService!!.getAlarmList(token!!)
        getAlarmList.enqueue(object : Callback<AlarmResult> {
            override fun onResponse(call: Call<AlarmResult>?, response: Response<AlarmResult>?) {
                if (response!!.isSuccessful) {
                    if (response.body().status == 200) {
                        alarmDataNew = response.body().result.not_read
                        alarmAdapterNew!!.setAdapter(alarmDataNew!!)
                        alarmAdapterNew!!.notifyDataSetChanged()

                        alarmDataOld = response.body().result.is_read
                        alarmAdapterOld!!.setAdapter(alarmDataOld!!)
                        alarmAdapterOld!!.notifyDataSetChanged()

                    } else {
                        ApplicationController.instance!!.makeToast("검색실패")
                    }
                }
            }


            override fun onFailure(call: Call<AlarmResult>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        networking()
    }
}
