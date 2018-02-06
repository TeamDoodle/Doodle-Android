package com.real.doodle.service

import android.util.Log
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Dayoung on 2018-01-04.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    private var networkService: NetworkService?=null
    // [START refresh_token]
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val token = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + token!!)

        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        sendRegistrationToServer(token)
    }


    private fun sendRegistrationToServer(token: String?) {
        networkService = ApplicationController.instance!!.networkService
        val token = FirebaseInstanceId.getInstance().token
        val alarmResponse=networkService!!.alarm(AlarmPost(token.toString()))
        alarmResponse.enqueue(object : Callback<AlarmResponse>{
            override fun onResponse(call: Call<AlarmResponse>?, response: Response<AlarmResponse>?) {
                if(response!!.isSuccessful){
                    Log.d("push","success push alarm")
                }else{

                }
            }

            override fun onFailure(call: Call<AlarmResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신상태를 확인해주세요")
            }

        })


    }

    companion object {
        private val TAG = "MyFirebaseIIDService"
    }
}