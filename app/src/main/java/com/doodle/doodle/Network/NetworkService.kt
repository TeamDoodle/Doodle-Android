package com.doodle.doodle.Network

import com.doodle.doodle.Doodle_Comment.CommentPostResponse
import com.doodle.doodle.Doodle_Comment.CommentResponse
import com.doodle.doodle.Doodle_Comment.PostCommentPost
import com.doodle.doodle.Doodle_Read.GetFeedPost
import com.doodle.doodle.Doodle_Read.GetFeedResponse
import com.doodle.doodle.Like.LikePost
import com.doodle.doodle.Like.LikeResponse
import com.doodle.doodle.Doodle_Write.PostResponse
import com.doodle.doodle.Login.LoginPost
import com.doodle.doodle.Login.LoginResponse
import com.doodle.doodle.Scrap.ScrapPost
import com.doodle.doodle.Scrap.ScrapResponse
import com.doodle.doodle.SignUp.DuplicateResponse
import com.doodle.doodle.SignUp.SignUpResponse
import com.doodle.doodle.service.AlarmPost
import com.doodle.doodle.service.AlarmResponse
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

//    글작성

    //    글작성
    @Multipart
    @POST("doodle/post")
    fun post(
            @Header("token") token : String,
            @Part("text") text:RequestBody,
            @Part image:MultipartBody.Part?):Call<PostResponse>
//    좋아요
    @POST("like/{idx}")
    fun like(
        @Header("token") token:String,
        @Path("idx") idx:Int,
        @Body likePost: LikePost):Call<LikeResponse>

//    담기
    @POST("scrap/{idx}")
    fun scrap(
        @Header("token") token:String,
        @Path("idx") idx: Int,
        @Body scrapPost:ScrapPost):Call<ScrapResponse>

    //댓글조회
    @GET("comments/{idx}")
    fun getComment(
            @Header("token") token : String,
            @Path("idx") idx: Int
    ) : Call<CommentResponse>

//    푸쉬알람
    @POST("alarm/token")
    fun alarm(
            @Body alarmPost:AlarmPost
    ): Call<AlarmResponse>

    //댓글작성
    @POST("comments/{idx}")
    fun postComment(
            @Header("token") token : String,
            @Path("idx") idx: Int,
            @Body postCommentPost : PostCommentPost
    ) : Call<CommentPostResponse>

}