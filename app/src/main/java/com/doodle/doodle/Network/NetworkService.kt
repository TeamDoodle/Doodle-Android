package com.doodle.doodle.Network

import com.doodle.doodle.Doodle_Read.GetFeedPost
import com.doodle.doodle.Doodle_Read.GetFeedResponse
import com.doodle.doodle.Login.LoginPost
import com.doodle.doodle.Login.LoginResponse
import com.doodle.doodle.SignUp.DuplicateResponse
import com.doodle.doodle.SignUp.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Jinyoung on 2018-01-03.
 */
interface NetworkService {
//    로그인
    @POST("users/login")
    fun login(@Body loginPost: LoginPost): Call<LoginResponse>

//    회원가입
    @Multipart
    @POST("users/register")
    fun sign(
        @Part("email") email:RequestBody,
        @Part("pw1") pw1:RequestBody,
        @Part("pw2") pw2:RequestBody,
        @Part("nickname") nickname:RequestBody,
        @Part image:MultipartBody.Part?):Call<SignUpResponse>

//    회원가입 이메일, 필명 중복체크
    @Multipart
    @POST("users/duplicates")
    fun duplicate(@Part("nickname") nickname: RequestBody,
                  @Part("email")email: RequestBody,
                  @Part("flag")flag:Int):Call<DuplicateResponse>

//    게시글 리스트 조회
    @POST("doodle/all")
    fun getFeed(
        @Header("token") token : String,
        @Body getFeedPost: GetFeedPost):Call<GetFeedResponse>
}