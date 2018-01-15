package com.doodle.doodle.Doodle_Single

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Comment.CommentActivity
import com.doodle.doodle.Doodle_Others.OtherActivity
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.Like.LikePost
import com.doodle.doodle.Like.LikeResponse
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import com.doodle.doodle.Scrap.ScrapPost
import com.doodle.doodle.Scrap.ScrapResponse
import kotlinx.android.synthetic.main.activity_single.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleActivity : AppCompatActivity() {
    private var idx: Int? = null
    private var networkService: NetworkService? = null
    private var requestManager: RequestManager? = null
    private var like:Int?=null
    private var scrap:Int?=null
    private var id:Int?=null
    private var comment_idx:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        networkService = ApplicationController.instance!!.networkService
        requestManager = Glide.with(this)
//                  받아온 인덱스 값 저장
        idx = intent.getIntExtra("idx", 0)
        getSingle(idx!!)

        //        닉네임 눌렀을 때 그사람 피드로 감
        single_nickname.setOnClickListener {
            val intent: Intent = Intent(applicationContext, OtherActivity::class.java)
            intent.putExtra("user_idx", idx!!)

            startActivity(intent)
        }

//        공감
        single_like.setOnClickListener {
            var likeString:String?=null
            if(like!=0){
                likeString="unlike"
            }else{
                likeString="like"
            }
            var getLike=networkService!!.like(getToken("token"),idx!!, LikePost(likeString!!))
            getLike.enqueue(object :Callback<LikeResponse>{
                override fun onResponse(call: Call<LikeResponse>?, response: Response<LikeResponse>?) {
                    if (response!!.isSuccessful){
//                            만약 좋아요가 되어 있다면,
                        if(like!=0){
                           like=0
                            single_like.typeface =
                                    Typeface.createFromAsset(assets, "fonts/nanum_myeongjo.ttf")
                        }
//                            좋아요가 안 되어 있다면,
                        else{
                            like=idx
                            single_like.typeface =
                                    Typeface.createFromAsset(assets, "fonts/nanum_myeongjo_extra_bold.ttf")
                        }
                       single_likeCount.text=response.body().result.count.toString()
                    }
                }
                override fun onFailure(call: Call<LikeResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("통신 상태를 확인해 주세요")
                }


            })
        }

//        댓글
        single_comment.setOnClickListener{
            var intent =Intent(applicationContext, CommentActivity::class.java)
            intent.putExtra("idx", comment_idx!!)
            startActivity(intent)
        }

//        담아감
       single_scrap.setOnClickListener {
            //            통신 시작
            var scrapString: String? = null
            if (scrap != 0) {
//                flag=4는 scrap에 담겨 있는 글
                scrapString = "unscrap"
            } else {
                scrapString = "scrap"
            }
            var getScrap = networkService!!.scrap(getToken("token"), idx!!, ScrapPost(scrapString!!))
            getScrap.enqueue(object : Callback<ScrapResponse> {
                override fun onResponse(call: Call<ScrapResponse>?, response: Response<ScrapResponse>?) {
                    if(response!!.isSuccessful){
                        single_scrapCount.text=response.body().result.count.toString()
                        if(scrap==0){
                            scrap=idx!!
                        }
                    }
                }

                override fun onFailure(call: Call<ScrapResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }
            })
        }
    }

    fun getToken(key: String): String {
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

    fun getSingle(idx: Int?) {
        var single = networkService!!.single(getToken("token"), idx!!)
        single.enqueue(object : Callback<SingleResponse> {
            override fun onResponse(call: Call<SingleResponse>?, response: Response<SingleResponse>?) {
                if (response!!.isSuccessful) {
                    if (response.body().result.image == null) {
                        requestManager!!.load(R.drawable.mytext1).into(single_image)
                    } else {
                        requestManager!!.load(response.body().result.image).into(single_image)
                    }
                    comment_idx=response.body().result.idx
                    single_date.text = response.body().result.created
                    single_likeCount.text = response.body().result.like_count.toString()
                    single_commentCount.text = response.body().result.comment_count.toString()
                    single_scrapCount.text = response.body().result.scrap_count.toString()
                    single_nickname.text = response.body().result.nickname
                    if (response.body().result.like !== 0) {
                        like=response.body().result.like
                        single_like.typeface =
                                Typeface.createFromAsset(assets, "fonts/nanum_myeongjo_extra_bold.ttf")
                    } else {
                        like=0
                        single_like.typeface =
                                Typeface.createFromAsset(assets, "fonts/nanum_myeongjo.ttf")
                    }
                    //담아감 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
                    if (response.body().result.scraps !== 0) {
                        scrap=response.body().result.scraps
                        single_scrap.typeface =
                                Typeface.createFromAsset(assets, "fonts/nanum_myeongjo_extra_bold.ttf")
                    } else {
                        scrap=0
                        single_scrap.typeface =
                                Typeface.createFromAsset(assets, "fonts/nanum_myeongjo.ttf")
                    }

                }
            }

            override fun onFailure(call: Call<SingleResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신 상태를 확인해주세요")
            }

        })
    }
}
