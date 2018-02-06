package com.real.doodle.Doodle_Read

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
import com.real.doodle.Doodle_Comment.CommentActivity
import com.real.doodle.Doodle_Me.CommonFeedData
import com.real.doodle.Doodle_Others.OtherActivity
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
 * Created by Jinyoung on 2018-01-05.
 */
class AppreciateDayAdapter(var con: Context,var feedList : ArrayList<FeedList>,var requestManager: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var networkService: NetworkService?=null
    private var idx:Int?=null
    private var id:Int?=null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var holder:AppreciateDayViewHolder=holder as AppreciateDayViewHolder
        getItemViewType(position)
        networkService=ApplicationController.instance!!.networkService
        idx= CommonFeedData.feedList!![position].idx
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
        }//좋아요 끝

        //        닉네임 눌렀을 때 그사람 피드로 감
        holder!!.nickname.setOnClickListener {
            val intent:Intent= Intent(con!!, OtherActivity::class.java)
            intent.putExtra("user_idx",feedList!!.get(position).user_idx)
            con.startActivity(intent)
        }

        //            댓글
        holder!!.comment.setOnClickListener{
            id = feedList!!.get(position).idx
            var intent =Intent(con,CommentActivity::class.java)
            intent.putExtra("idx", id!!)
            con.startActivity(intent)
            Log.d("댓글",id.toString())

        }//댓글 끝

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
            if(!feedList!![position].nickname.equals(CommonData.loginData!!.profile!!.nickname)){
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
            }else{
                Toast.makeText(con!!,"나의 글을 담아갈 수 없습니다.",Toast.LENGTH_SHORT).show()
            }

        }


        if(getItemViewType(position)==VIEW_TYPE_TOP_RATE){
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext2).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.commentCount.text=feedList!!.get(position).comment_count.toString()
            holder!!.likeCount.text=feedList!!.get(position).like_count.toString()
            holder!!.scrapCount.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname
            holder!!.updated.text=feedList!!.get(position).created

        }
        else if(getItemViewType(position)==VIEW_TYPE_IMAGE){
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext2).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.commentCount.text=feedList!!.get(position).comment_count.toString()
            holder!!.likeCount.text=feedList!!.get(position).like_count.toString()
            holder!!.scrapCount.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname


        }
    }
//    토큰 값 가져오는 함수
    fun getToken(key : String) : String{
        val prefs = con.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

//    onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AppreciateDayViewHolder? {
        var mainView:View?=null
        when(viewType){
            VIEW_TYPE_TOP_RATE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.day_top_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateDayViewHolder(mainView!!)
            }
            VIEW_TYPE_IMAGE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.day_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateDayViewHolder(mainView!!)
            }
        }
        return AppreciateDayViewHolder(mainView!!)
    }

    override fun getItemCount(): Int =feedList!!.size
    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    private val VIEW_TYPE_TOP_RATE : Int = 1
    private val VIEW_TYPE_IMAGE : Int = 2
    override fun getItemViewType(position: Int): Int {
        when(position){
            0->{
                return VIEW_TYPE_TOP_RATE
            }
            1->{
                return VIEW_TYPE_IMAGE
            }
            else->{
                return VIEW_TYPE_IMAGE
            }
        }
    }
}