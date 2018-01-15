package com.doodle.doodle.service

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.doodle.doodle.Alarm.AlarmActivity
import com.doodle.doodle.Alarm.AlarmClickData
import com.doodle.doodle.Alarm.AlarmItemResult
import com.doodle.doodle.Doodle_Single.SingleActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import com.google.firebase.messaging.RemoteMessage
import me.leolin.shortcutbadger.ShortcutBadger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dayoung on 2018-01-04.
 */
class MyFirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    private val alarmType = 1000
    private var networkService:NetworkService ?= null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //추가한것

        //remoteMessage : 서버에서준메세지
        Log.i("message", remoteMessage!!.data.toString())
        if (remoteMessage!!.data["type"]!!.toInt() == alarmType) {
            sendNotification(remoteMessage!!.data["title"].toString(),
                    remoteMessage!!.data["body"].toString(),
                    remoteMessage!!.data["idx"]!!.toInt())
        }
        count()
    }

    //알람이 왔을 때 해당 알람 카운트 통신을 해서 badge 달아주는거
    //activiy 실행하고 있을 시 알람 바꿔주기
    fun count(){
        networkService = ApplicationController.instance!!.networkService!!
        var pref:SharedPreferences ?= null
        pref = getSharedPreferences("token",Context.MODE_PRIVATE)
        var alarmCount = networkService!!.alarmCount(pref.getString("token",""))
        alarmCount.enqueue(object : Callback<AlarmCountResult> {
            override fun onResponse(call: Call<AlarmCountResult>?, response: Response<AlarmCountResult>?) {
                if (response!!.isSuccessful) {
                    if (response.body().status == 200) {
                        ShortcutBadger.applyCount(this@MyFirebaseMessagingService, response.body().result.count)
                    } else {
                        ApplicationController.instance!!.makeToast("검색실패")
                    }
                }
            }


            override fun onFailure(call: Call<AlarmCountResult>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }

    private fun sendNotification(title: String, body: String, idx: Int) {
        //TODO 해당 게시글로 이동
        val intent = Intent(this, AlarmActivity::class.java)
        intent.putExtra("idx",idx!!)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        //push 알람 오게 하는 부분
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)//.로고 아이콘 넣어라
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}