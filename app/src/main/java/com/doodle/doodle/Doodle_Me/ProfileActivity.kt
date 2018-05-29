package com.doodle.doodle.Doodle_Me

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Login.CommonData
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileActivity : AppCompatActivity() {
    private var requestManager:RequestManager?=null
    private var networkService: NetworkService?=null
    private var image: MultipartBody.Part? = null
    private var doesItChange : Int? = 1
    private val REQ_CODE_SELECT_IMAGE=100
    private val REQ_CODE=100
    private var data: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        networkService= ApplicationController.instance!!.networkService
        doesItChange=1

        requestManager=Glide.with(this)
        requestManager!!.load(CommonData.loginData!!.profile.profile!!)
                .into(profilePhoto)



        //         앨범 접근 해서 사진 가져오기
        profile_changeimage.setOnClickListener {
            changeImage()
        }


        profile_commit.setOnClickListener{
            //            val b = Bitmap.createBitmap(profilePhoto.getDrawingCache())
//            profilePhoto.setDrawingCacheEnabled(false)
//            val bytes = ByteArrayOutputStream()
//            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//
//            val f = File(Environment.getExternalStorageDirectory().toString() +
//                    File.separator +System.currentTimeMillis().toString()+".jpg")
//
//            val photoBody = RequestBody.create(MediaType.parse("image/jpg"), bytes.toByteArray())
//            image = MultipartBody.Part.createFormData("image", f.name, photoBody)

            putProfile(doesItChange)
            val intent: Intent = Intent()
            intent.putExtra("description", profileDescription.text.toString())
            intent.putExtra("profileimage", data.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
//        write_edit.bringToFront()
    }

    fun getToken(key : String) : String{
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

    fun putProfile(flag:Int?){
        val description = profileDescription.text.toString()
        var putProfileResponse=networkService!!.putProfile(getToken("token"), image, flag!!, description)
        putProfileResponse.enqueue(object : Callback<PutProfileResponse> {
            override fun onResponse(call: Call<PutProfileResponse>?, response: Response<PutProfileResponse>?) {
                if (response!!.isSuccessful){
                    if(response.body().message=="success"){
                        Log.d("슈발","슈발")
                        Log.d("슈발",description)
                        CommonData.loginData!!.profile.description = profileDescription.text.toString()
                        if(image!=null)
                            CommonData.loginData!!.profile.profile = data.toString()
                    }else{
                        ApplicationController.instance!!.makeToast("프로필을 수정할 수 없습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<PutProfileResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }

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
                            .into(profilePhoto)
                    doesItChange=2

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}