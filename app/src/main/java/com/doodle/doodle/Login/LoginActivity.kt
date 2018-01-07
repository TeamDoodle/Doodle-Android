package com.doodle.doodle.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.doodle.doodle.Main.MainActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var networkService:NetworkService?=null
    private var sharedPreference:SharedPreferences?=null
    private var editor:SharedPreferences.Editor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        networkService=ApplicationController.instance!!.networkService
        sharedPreference=getSharedPreferences("token", Context.MODE_PRIVATE)
        editor=sharedPreference!!.edit()

        login_btn.setOnClickListener {
             login()
        }
    }

    fun login(){

        val loginResponse=networkService!!.login(LoginPost(login_email.text.toString(),login_password.text.toString()))
        loginResponse.enqueue(object : Callback<LoginResponse>{
//           성공 시
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                Log.d("onResponse","onResponse")
            if(response!!.isSuccessful){
                Log.d("success","success")
                    if(response.body().status==200){
//                    CommonData 안에 loginProfile,token 넣어줌
//                        loginData=>(profile, token)
                        CommonData.loginData=response.body().result

//                      토큰 데이터에 저장 해놓음
                        editor!!.putString("token",response.body().result.token)
                        editor!!.commit()

                        Log.d("token",response.body().result.token)
                        ApplicationController.instance!!.makeToast("로그인 성공")
                        startActivity(Intent(applicationContext,MainActivity::class.java))
                    }else{
                        ApplicationController.instance!!.makeToast("이메일과 비번을 다시 확인해주세요")
                    }
                }
            }
//           실패 시
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요!!")
            }
        })
    }
}
