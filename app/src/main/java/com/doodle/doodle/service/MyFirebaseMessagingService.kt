package com.doodle.doodle.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.doodle.doodle.Alarm.AlarmActivity
import com.doodle.doodle.R
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Dayoung on 2018-01-04.
 */
class MyFirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    private val alarmType = 1000

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
    private fun count() {


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
                .setSmallIcon(R.drawable.alarm)//로고 아이콘 넣어라
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}