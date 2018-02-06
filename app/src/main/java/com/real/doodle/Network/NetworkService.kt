package com.real.doodle.Network

import android.support.annotation.Nullable
import com.real.doodle.Alarm.AlarmClickData
import com.real.doodle.service.AlarmCountResult
import com.real.doodle.Alarm.AlarmItemResult
import com.real.doodle.Alarm.AlarmResult
import com.real.doodle.Doodle_Comment.CommentPostResponse
import com.real.doodle.Doodle_Comment.CommentResponse
import com.real.doodle.Doodle_Comment.PostCommentPost
import com.real.doodle.Doodle_Me.DeleteResponse
import com.real.doodle.Doodle_Me.GetProfileResponse
import com.real.doodle.Doodle_Me.PutProfileResponse
import com.real.doodle.Doodle_Others.OtherResponse
import com.real.doodle.Doodle_Read.GetFeedPost
import com.real.doodle.Doodle_Read.GetFeedResponse
import com.real.doodle.Doodle_Search.SearchNameResponse
import com.real.doodle.Doodle_Search.SearchResponse
import com.real.doodle.Doodle_Single.SingleResponse
import com.real.doodle.Like.LikePost
import com.real.doodle.Like.LikeResponse
import com.real.doodle.Doodle_Write.PostResponse
import com.real.doodle.Doodle_Write.TodayImageResponse
import com.real.doodle.Login.LoginPost
import com.real.doodle.Login.LoginResponse
import com.real.doodle.Scrap.ScrapPost
import com.real.doodle.Scrap.ScrapResponse
import com.real.doodle.SignUp.DuplicatePost
import com.real.doodle.SignUp.DuplicateResponse
import com.real.doodle.SignUp.SignUpResponse
import com.real.doodle.service.AlarmPost
import com.real.doodle.service.AlarmResponse
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
            @Part("email") email: RequestBody,
            @Part("pw1") pw1: RequestBody,
            @Part("pw2") pw2: RequestBody,
            @Part("nickname") nickname: RequestBody,
            @Part image: MultipartBody.Part?): Call<SignUpResponse>

    //    회원가입 (이메일/필명) 중복체크
    @POST("users/duplicates")
    fun duplicate(@Body duplicatePost: DuplicatePost): Call<DuplicateResponse>

    //    게시글 리스트 조회
    @POST("doodle/all")
    fun getFeed(
            @Header("token") token: String,
            @Body getFeedPost: GetFeedPost): Call<GetFeedResponse>


    //    글작성
    @Multipart
    @POST("doodle/post")
    fun post(
            @Header("token") token: String,
            @Part("text") text: RequestBody,
            @Part image: MultipartBody.Part?): Call<PostResponse>

    //    좋아요
    @POST("like/{idx}")
    fun like(
            @Header("token") token: String,
            @Path("idx") idx: Int,
            @Body likePost: LikePost): Call<LikeResponse>

    //    담기
    @POST("scrap/{idx}")
    fun scrap(
            @Header("token") token: String,
            @Path("idx") idx: Int,
            @Body scrapPost: ScrapPost): Call<ScrapResponse>

    //댓글조회
    @GET("comments/{idx}")
    fun getComment(
            @Header("token") token: String,
            @Path("idx") idx: Int
    ): Call<CommentResponse>

    // 글작성 기본 배경 이미지 제공
    @GET("doodle/get")
    fun getImage(): Call<TodayImageResponse>

    //    푸쉬알람
    @POST("alarm/token")
    fun alarm(
            @Body alarmPost: AlarmPost
    ): Call<AlarmResponse>

    //댓글작성
    @POST("comments/{idx}")
    fun postComment(
            @Header("token") token: String,
            @Path("idx") idx: Int,
            @Body postCommentPost: PostCommentPost
    ): Call<CommentPostResponse>

    //프로필 수정
    @Multipart
    @PUT("users/modify")
    fun putProfile(
            @Header("token") token: String,
            @Nullable @Part image: MultipartBody.Part?,
            @Part("flag") flag: Int,
            @Part("description") description: String
    ): Call<PutProfileResponse>

    //    상대방이 올린 글 리스트 조회
    @GET("users/other/{idx}")
    fun getOthers(
            @Header("token") token: String,
            @Path("idx") idx: Int): Call<OtherResponse>

    //     필명검색
    @GET("search/users/{keyword}")
    fun getNameSearch(
            @Header("token") token: String,
            @Path("keyword") keyword: String): Call<SearchNameResponse>

    //    글귀 검색
    @GET("search/doodle/{keyword}")
    fun getWording(
            @Header("token") token: String,
            @Path("keyword") keyword: String): Call<SearchResponse>

    //    게시글 삭제(나의 글)
    @DELETE("doodle/delete/{idx}")
    fun delete(
            @Header("token") token: String,
            @Path("idx") idx: Int): Call<DeleteResponse>

    //      상세 페이지
    @GET("doodle/get/{idx}")
    fun single(
            @Header("token") token: String,
            @Path("idx") idx: Int): Call<SingleResponse>

    //알람 리스트 조회
    @GET("alarm/list")
    fun getAlarmList(
            @Header("token") token: String
    ): Call<AlarmResult>

    //알람 아이템 클릭
    @POST("alarm/item")
    fun alarmItemClick(
            @Header("token") token: String,
            @Body alarmClickData: AlarmClickData
    ): Call<AlarmItemResult>

    //알람 카운트
    @GET("alarm/count")
    fun alarmCount(
            @Header("token") token: String
    ): Call<AlarmCountResult>

    //    프로필 조회
    @GET("users")
    fun getProfile(
            @Header("token") token: String
    ):Call<GetProfileResponse>

}
