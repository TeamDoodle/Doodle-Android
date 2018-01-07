package com.doodle.doodle.Doodle_Me

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.Doodle_Read.GetFeedPost
import com.doodle.doodle.Doodle_Read.GetFeedResponse
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context.MODE_PRIVATE
/**
 * Created by SAMSUNG on 2018-01-01.
 */
class GatherFragment: Fragment(), View.OnClickListener {
    private var networkService:NetworkService?=null
    private var feedRecyclerView:RecyclerView?=null
    private var feedList : ArrayList<FeedList>? = null
    private var feedAdapter : FeedAdapter? = null
    private var requestManager:RequestManager?=null
    private var myfeed_flag:Int?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_gather,container,false) //레이아웃을 붙이는 과정
        networkService=ApplicationController.instance!!.networkService
        myfeed_flag=arguments.getInt("flag")
        getFeed(myfeed_flag!!)
        requestManager= Glide.with(this)
        if(arguments!=null){

        }
        feedRecyclerView=v.findViewById(R.id.feed_recyclerview)
        feedRecyclerView!!.layoutManager= GridLayoutManager(this.context,2) as RecyclerView.LayoutManager?
        feedRecyclerView!!.isNestedScrollingEnabled = false
        return v

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getToken(key : String) : String{
        val prefs = context.getSharedPreferences("token", MODE_PRIVATE)
        return prefs.getString(key, "")
    }

    fun getFeed(flag:Int?){
        Log.d("flag",flag.toString())
        var getFeedResponse=networkService!!.getFeed(getToken("token"), GetFeedPost(flag!!))
        getFeedResponse.enqueue(object :Callback<GetFeedResponse>{
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                if (response!!.isSuccessful){
                    if(response.body().status==200){
                        feedList = response.body().result
                        feedAdapter= FeedAdapter(feedList!!,requestManager!!)
                        feedRecyclerView!!.adapter=feedAdapter
                        feedAdapter!!.setOnItemClickListener(this@GatherFragment)


                    }else{
                        ApplicationController.instance!!.makeToast("피드를 가져올 수 없습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<GetFeedResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }

    override fun onClick(v: View?) {
        val idx : Int = feedRecyclerView!!.getChildAdapterPosition(v)
        val feedType : Int = 1
//      사진 클릭 시 한줄보기로 넘어감
        val intent = Intent(activity, MyfeedBigActivity::class.java)
//        feedType 넘겨줌
        intent.putExtra("feedType", feedType)
        intent.putExtra("image",feedList!!.get(idx).image)
        intent.putExtra("idx",feedList!!.get(idx).idx)
        intent.putExtra("updated", feedList!!.get(idx).updated)
        intent.putExtra("like_count",feedList!!.get(idx).like_count)
        intent.putExtra("comment_count",feedList!!.get(idx).comment_count)
        intent.putExtra("scrap_count",feedList!!.get(idx).scrap_count)
        intent.putExtra("nickname",feedList!!.get(idx).nickname)
        //intent.putExtra("type", type)
        //intent.putExtra("name", name)
        startActivity(intent)

    }



}