package com.real.doodle.Splash

import android.Manifest
import android.content.Context
import com.real.doodle.Main.MainActivity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.*
import android.util.Log
import com.real.doodle.Login.CommonData
import com.real.doodle.Login.LoginActivity
import com.real.doodle.Login.LoginPost
import com.real.doodle.Login.LoginResponse
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R
import com.real.doodle.Tutorial.Tutorial1
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private var email: String? = null
    private var pw: String? = null
    private var networkService: NetworkService? = null

    private val REQUEST_CODE_MULTI = 1001
    private val REQUEST_CODE_READ = 1002
    private val REQUEST_CODE_WRITE = 1002
    private var sharedPreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    //    자동로그인
    private var sharedPreference_login: SharedPreferences? = null
    private var editor_login: SharedPreferences.Editor? = null
//     튜토리얼
    private var sharedPrefernece_tut:SharedPreferences?=null
    private var editor_tut:SharedPreferences.Editor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        networkService = ApplicationController.instance!!.networkService
        networkService = ApplicationController.instance!!.networkService
        sharedPreference = getSharedPreferences("token", Context.MODE_PRIVATE)
        editor = sharedPreference!!.edit()

        sharedPreference_login = getSharedPreferences("pref", Context.MODE_PRIVATE)
        editor_login = sharedPreference!!.edit()

        sharedPrefernece_tut=getSharedPreferences("tut", Context.MODE_PRIVATE)
        editor_tut=sharedPrefernece_tut!!.edit()
        editor_tut!!.putString("first","first")
        editor_tut!!.commit()

        email = sharedPreference_login!!.getString("email", "")
        pw = sharedPreference_login!!.getString("pw", "")

        sharedPreference = getSharedPreferences("first", Context.MODE_PRIVATE)
        editor = sharedPreference!!.edit()
        editor!!.putString("first","first")
        editor!!.commit()



        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here
            checkPermission()
        } else {
            start()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_MULTI) {
            val readPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED
            val writePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (readPermission && writePermission) {
                start()
            } else {
                if (!(readPermission && writePermission)) {
                    Toast.makeText(this@SplashActivity, "권한을 설정하여야 어플 사용이 가능합니다.", Toast.LENGTH_SHORT).show()

                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_MULTI)
                } else if (!readPermission) {
                    Toast.makeText(this@SplashActivity, "권한을 설정하여야 어플 사용이 가능합니다.", Toast.LENGTH_SHORT).show()

                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ)
                } else if (!writePermission) {
                    Toast.makeText(this@SplashActivity, "권한을 설정하여야 어플 사용이 가능합니다.", Toast.LENGTH_SHORT).show()

                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE)
                }
            }
        } else if (requestCode == REQUEST_CODE_WRITE) {
            val writePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (writePermission) {
                start()
            } else {
                Toast.makeText(this@SplashActivity, "권한을 설정하여야 어플 사용이 가능합니다.", Toast.LENGTH_SHORT).show()

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE)
            }
        } else if (requestCode == REQUEST_CODE_READ) {
            val readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (readPermission) {
                start()
            } else {
                Toast.makeText(this@SplashActivity, "권한을 설정하여야 어플 사용이 가능합니다.", Toast.LENGTH_SHORT).show()

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ)
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_MULTI)

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ)

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE)


            } else {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_MULTI)
            }
        } else {
            // write your logic code if permission already granted
            start()
        }
    }

    private fun start() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            var intent : Intent ?=null
            if (!email.equals("")){
                login(email!!,pw!!)
                intent = Intent(this@SplashActivity, MainActivity::class.java)

            } else if(sharedPrefernece_tut!!.getString("first","").equals("first")){
                intent = Intent(this@SplashActivity, Tutorial1::class.java)
                editor_tut!!.putString("first","notFirst")
                editor_tut!!.commit()
            }else if(!sharedPrefernece_tut!!.getString("first","").equals("first")){
                intent=Intent(this@SplashActivity,LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }, 1000)
    }

    fun login(email:String, pw:String) {

        val loginResponse = networkService!!.login(LoginPost(email!!,pw!!,
                FirebaseInstanceId.getInstance().token.toString()))
        loginResponse.enqueue(object : Callback<LoginResponse> {
            //           성공 시
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                Log.d("onResponse", "onResponse")
                if (response!!.isSuccessful) {
                    Log.d("success", "success")
                    if (response.body().status == 200) {
                        CommonData.loginData = response.body().result

//                      토큰 데이터에 저장 해놓음
                        editor!!.putString("token", response.body().result.token)
                        editor!!.commit()

//                        자동로그인을 위해 이메일과 패스워드를 저장한다
                        editor_login!!.putString("email", email!!)
                        editor_login!!.putString("pw", pw!!)
                        editor_login!!.commit()

                        Log.d("token", response.body().result.token)
                    } else {
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