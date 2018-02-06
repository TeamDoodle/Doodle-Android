package com.real.doodle.Doodle_Others

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Comment.CommentActivity
import com.real.doodle.Like.LikePost
import com.real.doodle.Like.LikeResponse
import com.real.doodle.Login.CommonData
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R
import com.real.doodle.Scrap.ScrapPost
import com.real.doodle.Scrap.ScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Jinyoung on 2018-01-11.
 */
class BigOtherAdapter(var con: Context, var doodleList: ArrayList<userDoodleData>,
                      var requestManager: RequestManager
) : RecyclerView.Adapter<BigOtherViewHolder>() {
    private var networkService: NetworkService? = null
    fun setAdapter(doodleList: ArrayList<userDoodleData>) {
        this.doodleList = doodleList
    }
    override fun onBindViewHolder(holder: BigOtherViewHolder?, position: Int) {
        requestManager= Glide.with(con!!)
        if (doodleList!![position].image == null) {
            requestManager!!.load(R.drawable.mytext2).into(holder!!.BigOtherImage)
        } else {
            requestManager!!.load(doodleList!![position].image).into(holder!!.BigOtherImage)
        }
        holder!!.BigOtherDate.text = doodleList!![position].created
        holder!!.BigOtherLikeCount.text = doodleList!![position].like_count.toString()
        holder!!.BigOtherScrapCount.text = doodleList!![position].scrap_count.toString()
        holder!!.BigOtherCommentCount.text = doodleList!![position].comment_count.toString()

        //좋아요 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (doodleList!![position].like !== 0) {
            holder!!.BigOtherLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.BigOtherLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }
//        좋아요
        holder!!.BigOtherLike.setOnClickListener {
            //통신 시작
            var likeString: String? = null
            if (doodleList!![position].like != null) {
                likeString = "unlike"
            } else {
                likeString = "like"
            }
            networkService = ApplicationController.instance!!.networkService
            var getLike = networkService!!.like(getToken("token"), doodleList!![position].idx!!, LikePost(likeString!!))
            getLike.enqueue(object : Callback<LikeResponse> {
                override fun onResponse(call: Call<LikeResponse>?, response: Response<LikeResponse>?) {
                    if (response!!.isSuccessful) {
                        if (doodleList!![position].like != 0) {
                            doodleList!![position].like = 0
                        } else {
                            doodleList!![position].like = doodleList!![position].idx
                        }
                        doodleList!![position].like_count = response.body().result.count
                        notifyItemChanged(position)
                    }
                }

                override fun onFailure(call: Call<LikeResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }

            })
            //          통신 끝
        }
        //        댓글
        holder!!.BigOtherComment.setOnClickListener {

            var intent = Intent(con, CommentActivity::class.java)
            intent.putExtra("idx", doodleList!!.get(position).idx!!)
            con.startActivity(intent)
        }
        holder!!.BigOtherCommentCount.text = doodleList!!.get(position).comment_count.toString()
//        댓글 끝

        //담아감 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (doodleList!![position].scraps != 0) {
            holder!!.BigOtherScrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.BigOtherScrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }
//       담아감
        holder!!.BigOtherScrap.setOnClickListener {
            //            통신 시작
            if(!doodleList!![position].scraps!!.equals(CommonData.loginData!!.profile.nickname)){
                var scrapString: String? = null
                Log.d("fat",doodleList.get(position).toString())
                scrapString = if (doodleList!![position].scraps != 0) {
                    "unscrap"
                } else {
                    "scrap"
                }

                networkService = ApplicationController.instance!!.networkService
                var getScrap = networkService!!.scrap(getToken("token"), doodleList!![position].idx!!, ScrapPost(scrapString!!))
                getScrap.enqueue(object : Callback<ScrapResponse> {
                    override fun onResponse(call: Call<ScrapResponse>?, response: Response<ScrapResponse>?) {
                        if (response!!.isSuccessful) {
//                        doodleList!![position].scrap_count=response.body().result.count
                            if (doodleList!![position].scraps != 0) {
                                doodleList!![position].scraps = 0
//                            doodleList!!.remove(doodleList!!.get(position))
//                            notifyItemRemoved(position)
                            } else {
                                doodleList!![position].scraps = doodleList!![position].idx
                            }
                            doodleList!![position].scrap_count = response.body().result.count
                            notifyItemChanged(position)

                        }
                    }

                    override fun onFailure(call: Call<ScrapResponse>?, t: Throwable?) {
                        ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                    }
                })
            }

        }

//        담아감 끝

    }

    override fun getItemCount(): Int = doodleList!!.size
    private var onItemClick: View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BigOtherViewHolder {
        val mainView: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.big_other_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return BigOtherViewHolder(mainView)
    }


    //    통신
//  토큰 받아오는 함수
    fun getToken(key: String): String {
        val prefs = con!!.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }
}