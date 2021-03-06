package com.doodle.doodle.Login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.doodle.doodle.Doodle_Write.WriteActivity
import com.doodle.doodle.Main.MainActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import com.doodle.doodle.SignUp.SignUpActivity
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var networkService:NetworkService?=null
    private var sharedPreference:SharedPreferences?=null
    private var editor:SharedPreferences.Editor?=null
//    자동로그인
    private var sharedPreference_login:SharedPreferences?=null
    private var editor_login:SharedPreferences.Editor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        networkService=ApplicationController.instance!!.networkService
        sharedPreference=getSharedPreferences("token", Context.MODE_PRIVATE)
        editor=sharedPreference!!.edit()

        sharedPreference_login=getSharedPreferences("pref",Context.MODE_PRIVATE)
        editor_login=sharedPreference_login!!.edit()

        login_btn.setOnClickListener {
             login()
        }
        goto_signup.setOnClickListener { startActivity(Intent(applicationContext,SignUpActivity::class.java)) }
    }

    fun login(){

        val loginResponse=networkService!!.login(LoginPost(login_email.text.toString(),login_password.text.toString()
        , FirebaseInstanceId.getInstance().token.toString()))
        loginResponse.enqueue(object : Callback<LoginResponse>{
//           성공 시
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                Log.d("onResponse","onResponse")
            if(response!!.isSuccessful){
                Log.d("success","success")
                    if(response.body().status==200){
                        CommonData.loginData=response.body().result

//                      토큰 데이터에 저장 해놓음
                        editor!!.putString("token",response.body().result.token)
                        editor!!.commit()

//                        자동로그인을 위해 이메일과 패스워드를 저장한다
                        editor_login!!.putString("email",login_email.text.toString())
                        editor_login!!.putString("pw",login_password.text.toString())
                        editor_login!!.commit()

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
