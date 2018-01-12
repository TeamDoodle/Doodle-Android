package com.doodle.doodle.Alarm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Read.AppreciateActivity
import com.doodle.doodle.Doodle_Single.SingleActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dayoung on 2018-01-01.
 */
class AlarmAdapter(private var con: Context, private var dataList:ArrayList<AlarmData>?, private var requestManager: RequestManager): RecyclerView.Adapter<AlarmViewHolder>(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    var networkService:NetworkService ?= null
    var token:String?=null
    var pref:SharedPreferences ?= null


    override fun onBindViewHolder(holder: AlarmViewHolder?, position: Int) {
//        holder!!.userImage. = dataList!![position].ImageData.toString()
        var name:String = dataList!![position].nickname + "님"
        if(dataList!![position].count != 1){
            name = name + "외 " + (dataList!![position].count-1) +"명"
        }
        var description:String = ""
        when(dataList!![position].flag){
            (1)->{
                description += "이 이 글을 공감하였습니다."
            }
            (2)->{
                description += "이 이 글에 댓글을 달았습니다."
            }
            (3)->{
                description += "이 이 글을 담아 갔습니다."
            }
        }
        holder!!.userName.text = name
        holder!!.description.text = description
        holder!!.allLay.setOnClickListener {
            var alarmItemData: AlarmClickData?=null
            alarmItemData = AlarmClickData(dataList!![position].flag,dataList!![position].doodle_idx,dataList!![position].idx)
            networking(alarmItemData)
        }
    }
    fun setAdapter(dataList: ArrayList<AlarmData>){
        this.dataList = dataList
    }
    fun setNetworkservice(networkService: NetworkService){
        this.networkService = networkService
    }
    override fun getItemCount(): Int = dataList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlarmViewHolder {

        val mainView : View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.alarm_list_item,parent,false)

        pref = con!!.getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token","")


        return AlarmViewHolder(mainView)

    }
    fun networking(alarmItemData: AlarmClickData){
        var alarmItemClick = networkService!!.alarmItemClick(token!!,alarmItemData)
        alarmItemClick.enqueue(object : Callback<AlarmItemResult> {
            override fun onResponse(call: Call<AlarmItemResult>?, response: Response<AlarmItemResult>?) {
                if (response!!.isSuccessful) {
                    if (response.body().status == 200) {
                        //TODO 액티비티 연결해조
                        var intent : Intent = Intent(con!!, SingleActivity::class.java)
                        intent.putExtra("idx",alarmItemData.doodle_idx!!)
                        startActivityForResult(con as Activity?,intent,0,null)

                    } else {
                        ApplicationController.instance!!.makeToast("검색실패")
                    }
                }
            }


            override fun onFailure(call: Call<AlarmItemResult>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }
}