package com.doodle.doodle.SignUp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.doodle.doodle.Login.LoginActivity
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private val REQ_CODE=100
    private var data: Uri?=null
    private var image: MultipartBody.Part?=null
    private var networkService: NetworkService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        networkService= ApplicationController.instance!!.networkService

        signup_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("afterTextChanged","afterTextChanged")
                duplicateCheck(1)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("beforeTextChanged","beforeTextChanged")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("onTextChanged","onTextChanged")
            }

        })

//        중복 체크 FocusChangeListener
//        signup_nickname.onFocusChangeListener = object :View.OnFocusChangeListener{
//
//            override fun onFocusChange(p0: View?, p1: Boolean) {
//                if(p1==false){
//                    Toast.makeText(applicationContext,"nickname 포커스 바뀜",Toast.LENGTH_SHORT).show()
//                    duplicateCheck(1)
//                }
//            }
//        }
//        signup_email.onFocusChangeListener=object :View.OnFocusChangeListener{
//            override fun onFocusChange(p0: View?, p1: Boolean) {
//                if(p1==false){
//                    Toast.makeText(applicationContext,"email 포커스 바뀜",Toast.LENGTH_SHORT).show()
//                    duplicateCheck(2)
//
//                }
//            }
//        }

//        회원가입 버튼
        signup_button.setOnClickListener {
        try{
            signup()

        } catch (e:Exception){
            Toast.makeText(applicationContext,"프로필 사진을 지정해주세요",Toast.LENGTH_SHORT).show()
        }
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }

//        프사 변경
        signup_change_picture.setOnClickListener { changeImage() }

//        기존계정이 있는 경우
        goto_login.setOnClickListener { startActivity(Intent(applicationContext,LoginActivity::class.java)) }
    }
//    회원가입
    fun signup(){
//    필명 유효성 검사
    if(signup_nickname.text.toString().length<1 && signup_nickname.text.toString().length>8){
        Toast.makeText(applicationContext,"1자에서 8자로 입력해주세요",Toast.LENGTH_SHORT).show()
        signup_nickname.requestFocus()
        return
    }
//    이메일 유효성 검사
    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(signup_email.text.toString()).matches()){
        Toast.makeText(applicationContext,"이메일을 다시 확인해주세요",Toast.LENGTH_SHORT).show()
        signup_email.requestFocus()
        return
    }
//    비밀번호 유효성 검사
    else if (!Pattern.matches("^[a-zA-Z0-9]*$$",signup_password.text.toString())){
        Toast.makeText(applicationContext,"비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show()
        signup_password.requestFocus()
        return
    }
    else if (signup_password.text.toString().length<8 && signup_password.text.toString().length>20){
        Toast.makeText(applicationContext,"비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show()
        signup_password.requestFocus()
        return
    }

    else{
        val email=RequestBody.create(MediaType.parse("text/plain"),signup_email.text.toString())
        val pw1=RequestBody.create(MediaType.parse("text/plain"),signup_password.text.toString())
        val pw2=RequestBody.create(MediaType.parse("text/plain"),signup_check_password.text.toString())
        val nickname=RequestBody.create(MediaType.parse("text/plain"),signup_nickname.text.toString())

        val signupResponse = networkService!!.sign(email,pw1,pw2,nickname,image!!)

        signupResponse.enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(call: Call<SignUpResponse>?, response: Response<SignUpResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("response","response")
                    ApplicationController.instance!!.makeToast("회원 가입 완료")
                }else{
                    ApplicationController.instance!!.makeToast("회원 가입 실패")
                }
            }
            override fun onFailure(call: Call<SignUpResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })
    }

}

//    필명 중복확인
    fun duplicateCheck(flag:Int?){
    Log.d("함수들어옴","함수 들어왔다 슈바")
// 필명 중복확인
    if(flag==1){
        val nickname=RequestBody.create(MediaType.parse("text/plain"),signup_nickname.text.toString())
        val duplicateResponse=networkService!!.duplicate(nickname,RequestBody.create(MediaType.parse("text/plain"),""),flag!!)
        duplicateResponse.enqueue(object : Callback<DuplicateResponse>{
            //            성공 시
            override fun onResponse(call: Call<DuplicateResponse>?, response: Response<DuplicateResponse>?) {
                Log.d("onResponse",flag.toString())
                if(response!!.isSuccessful){
                    Log.d("isSuccessful","isSuccessful")
                    if(response.body().status==200){
                        ApplicationController.instance!!.makeToast("사용 가능")
                    }
                    else if(response.body().status==400){
                        ApplicationController.instance!!.makeToast("중복됩니다ㅠㅠ")
                    }
                }
            }

            //            실패 시
            override fun onFailure(call: Call<DuplicateResponse>?, t: Throwable?) {
                Log.d("실패","onFailure")
                ApplicationController.instance!!.makeToast("서버 상태를 확인해주세요")
            }
        })
    }
//    이메일 중복확인
    else if(flag==2){
        val email=RequestBody.create(MediaType.parse("text/plain"),signup_email.text.toString())
        val duplicateResponse=networkService!!.duplicate(RequestBody.create(MediaType.parse("text/plain"),""),email,flag!!)
        duplicateResponse.enqueue(object : Callback<DuplicateResponse>{
            //            성공 시
            override fun onResponse(call: Call<DuplicateResponse>?, response: Response<DuplicateResponse>?) {
                Log.d("onResponse",flag.toString())
                if(response!!.isSuccessful){
                    Log.d("isSuccessful","isSuccessful")
                    if(response.body().status==200){
                        ApplicationController.instance!!.makeToast("사용 가능")
                    }
                    else if(response.body().status==400){
                        ApplicationController.instance!!.makeToast("중복됩니다ㅠㅠ")
                    }
                }
            }

            //            실패 시
            override fun onFailure(call: Call<DuplicateResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해주세요")
            }
        })
    }



    }

//    프로필 사진 변경
    fun changeImage(){
    val intent = Intent(Intent.ACTION_PICK)
    intent.type=android.provider.MediaStore.Images.Media.CONTENT_TYPE
    intent.data=android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    startActivityForResult(intent,REQ_CODE)
}

//    onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQ_CODE){
            if(resultCode== Activity.RESULT_OK){
                try {
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString())

                    image = MultipartBody.Part.createFormData("image", photo.name, photoBody)

                    Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(signup_profile)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}
