package com.doodle.doodle.Doodle_Read

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Comment.CommentActivity
import com.doodle.doodle.Doodle_Me.CommonFeedData
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

class AppreciateAdapter(var con:Context,var feedList : ArrayList<FeedList>,var requestManager: RequestManager) : RecyclerView.Adapter<AppreciateViewHolder>() {
    private var networkService:NetworkService?=null
    private var likeString:String?=null
    private var all_or_week:Int?=null
    private var id:Int?=null
    private var idx:Int?=null

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: AppreciateViewHolder?, position: Int) {
        all_or_week= CommonFeedData.feedList!![position].idx
        getItemViewType(position)
//      좋아요 굵은 글씨
        if (feedList!![position].like !== 0) {
            holder!!.like.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.like.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }
        ////            좋아요
        holder!!.like.setOnClickListener {
            var likeString:String?=null
            if(feedList!![position].like!=0){
                likeString="unlike"
            }else{
                likeString="like"
            }
            var getLike=networkService!!.like(getToken("token"),feedList[position].idx!!, LikePost(likeString!!))
            getLike.enqueue(object :Callback<LikeResponse>{
                override fun onResponse(call: Call<LikeResponse>?, response: Response<LikeResponse>?) {
                    if (response!!.isSuccessful){
//                            만약 좋아요가 되어 있다면,
                        if(feedList!![position].like!=0){
                            feedList!![position].like=0
                        }
//                            좋아요가 안 되어 있다면,
                        else{
                            feedList!![position].like=feedList!![position].idx
                        }
                        feedList!![position].like_count=response.body().result.count
                        notifyItemChanged(position)
                    }
                }
                override fun onFailure(call: Call<LikeResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("통신 상태를 확인해 주세요")
                }


            })
        }

        //담아감 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (feedList!![position].scraps !== 0) {
            holder!!.scrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.scrap.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }

//          담아감
        holder!!.scrap.setOnClickListener {
            //            통신 시작
            var scrapString: String? = null
            if (feedList!![position].scraps != 0 ) {
//                flag=4는 scrap에 담겨 있는 글
                scrapString = "unscrap"
            } else {
                scrapString = "scrap"
            }
            var getScrap = networkService!!.scrap(getToken("token"), feedList!![position].idx!!, ScrapPost(scrapString!!))
            getScrap.enqueue(object : Callback<ScrapResponse> {
                override fun onResponse(call: Call<ScrapResponse>?, response: Response<ScrapResponse>?) {
                    if(response!!.isSuccessful){
                        if(feedList!![position].scraps!=0){
                            feedList!![position].scraps=0
                        }else{
                            feedList!![position].scraps=feedList!![position].idx
                        }
                        feedList!![position].scrap_count=response.body().result.count
                        notifyItemChanged(position)
                    }
                }

                override fun onFailure(call: Call<ScrapResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }
            })
        }
//          댓글
        holder!!.comment.setOnClickListener{
            id = feedList!!.get(position).idx
            var intent =Intent(con,CommentActivity::class.java)
            intent.putExtra("idx", id!!)
            con.startActivity(intent)
            Log.d("댓글",id.toString())

        }
//        랭킹 붙은 부분일 때
        if(getItemViewType(position)==VIEW_TYPE_TOP_RATE){
            when(position){
                0-> holder!!.rankimage.setImageResource(R.drawable.rank1)
                1-> holder!!.rankimage.setImageResource(R.drawable.rank2)
                2-> holder!!.rankimage.setImageResource(R.drawable.rank3)
            }
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext1).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.commentcount.text=feedList!!.get(position).comment_count.toString()
            holder!!.likecount.text=feedList!!.get(position).like_count.toString()
            holder!!.scrapcount.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname
            networkService=ApplicationController.instance!!.networkService

        }

//        랭킹 안 붙은 부분일 때
        else if(getItemViewType(position)==VIEW_TYPE_IMAGE){
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext1).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.commentcount.text=feedList!!.get(position).comment_count.toString()
            holder!!.likecount.text=feedList!!.get(position).like_count.toString()
            holder!!.scrapcount.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname


        }

    }

    private val VIEW_TYPE_TOP_RATE : Int = 1
    private val VIEW_TYPE_IMAGE : Int = 2


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }
    fun getToken(key : String) : String{
        val prefs = con.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

    override fun getItemCount(): Int = feedList!!.size

    override fun getItemViewType(position: Int): Int {
        when(position){
            0->{
                return VIEW_TYPE_TOP_RATE
            }
            1->{
                return VIEW_TYPE_TOP_RATE
            }
            2->{
                return VIEW_TYPE_TOP_RATE
            }
            else->{
                return VIEW_TYPE_IMAGE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AppreciateViewHolder? {
        var mainView:View?=null
        when(viewType){
            VIEW_TYPE_TOP_RATE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.top_appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateViewHolder(mainView!!)
            }
            VIEW_TYPE_IMAGE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateViewHolder(mainView!!)
            }
        }
        return AppreciateViewHolder(mainView!!)
    }



}

