package com.doodle.doodle.Doodle_Me

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Comment.CommentActivity
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.Like.LikePost
import com.doodle.doodle.Like.LikeResponse
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import com.doodle.doodle.Scrap.ScrapPost
import com.doodle.doodle.Scrap.ScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedBigAdapter(var con: Context,
                     var postList: ArrayList<FeedList>,
                     var requestManager: RequestManager
) : RecyclerView.Adapter<BigFeedViewHolder>() {
    private var networkService: NetworkService? = null

    private var feed_or_scrap: Int? = null  //나의 글 인지 담은 글 인지 확인하는 flag
    private var flag: Int? = null   //setAdapter에서 쓰는 flag

    fun setAdapter(flag: Int, feedList: ArrayList<FeedList>) {
        this.postList = feedList
        this.flag = flag
    }

    //    onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BigFeedViewHolder? {

        val mainView: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.feed_big_item, parent, false) //레이아웃 연결
        mainView.setOnClickListener(onItemClick)
        if (flag == 3) {
            feed_or_scrap = CommonFeedData.flag!!
        } else if (flag == 4) {
            feed_or_scrap = CommonScrapData.flag!!
        }
        return BigFeedViewHolder(mainView)

    }

    //  onBindViewHolder
    override fun onBindViewHolder(holder: BigFeedViewHolder?, position: Int) {
        networkService = ApplicationController.instance!!.networkService
        var likeString: String? = null
//        사진
        if (postList!!.get(position).image == null) {
            requestManager!!.load(R.drawable.pa).into(holder!!.postBigImage)
        } else {
            requestManager!!.load(postList!!.get(position).image).into(holder!!.postBigImage)
        }
        holder!!.PostDate.text = postList!!.get(position).updated
        holder!!.WritingName.text = postList!!.get(position).nickname
        holder!!.bigScrap_count.text = postList!!.get(position).scrap_count.toString()
        holder!!.bigNickname.text = postList!!.get(position).nickname

//        이미지 세팅 눌렀을 때
        holder!!.imageSetting.setOnClickListener {
            Toast.makeText(con, "안녕", Toast.LENGTH_SHORT).show()
        }

        //좋아요 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (postList!![position].like !== 0) {
            holder!!.bigLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.bigLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }

//       좋아요
        holder!!.bigLike.setOnClickListener {
            //통신 시작
            var likeString: String? = null
            if (postList!![position].like != 0) {
                likeString = "unlike"
            } else {
                likeString = "like"
            }
            var getLike = networkService!!.like(getToken("token"), postList!![position].idx!!, LikePost(likeString!!))
            getLike.enqueue(object : Callback<LikeResponse> {
                override fun onResponse(call: Call<LikeResponse>?, response: Response<LikeResponse>?) {
                    if (response!!.isSuccessful) {
                        if (postList!![position].like != 0) {
                            postList!![position].like = 0
                        } else {
                            postList!![position].like = postList!![position].idx
                        }
                        postList!![position].like_count = response.body().result.count
                        notifyItemChanged(position)
                    }
                }

                override fun onFailure(call: Call<LikeResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }

            })
            //          통신 끝

        }
//      좋아요 끝

//        댓글
        holder!!.bigComment.setOnClickListener {

            var intent = Intent(con, CommentActivity::class.java)
            intent.putExtra("idx", postList!!.get(position).idx!!)
            con.startActivity(intent)
        }
        holder!!.bigComment_count.text = postList!!.get(position).comment_count.toString()
//        댓글 끝

        //담아감 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (postList!![position].scraps !== 0) {
            holder!!.bigScrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.bigScrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }
//       담아감
        holder!!.bigScrap.setOnClickListener {
            //            통신 시작
            var scrapString: String? = null
            if (postList!![position].scraps != 0 || flag==4) {
//                flag=4는 scrap에 담겨 있는 글
                scrapString = "unscrap"
            } else {
                scrapString = "scrap"
            }
            var getScrap = networkService!!.scrap(getToken("token"), postList!![position].idx!!, ScrapPost(scrapString!!))
            getScrap.enqueue(object : Callback<ScrapResponse> {
                override fun onResponse(call: Call<ScrapResponse>?, response: Response<ScrapResponse>?) {
                    if(response!!.isSuccessful){
                        postList!![position].scrap_count=response.body().result.count
                        if(postList!![position].scraps!=0){
                            postList!![position].scraps=0
                            postList!!.remove(postList!!.get(position))
                            notifyItemRemoved(position)
                        }else{
                            postList!![position].scraps=postList!![position].idx
                            notifyItemChanged(position)

                        }
                    }
                }

                override fun onFailure(call: Call<ScrapResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }
            })
        }

//        담아감 끝
    }


    private var onItemClick: View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun getItemCount(): Int = postList!!.size

    //  토큰 받아오는 함수
    fun getToken(key: String): String {
        val prefs = con.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }


}