package com.doodle.doodle.Doodle_Me

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_gather.*
import kotlinx.android.synthetic.main.fragment_scrap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class ScrapFragment : Fragment(), View.OnClickListener  {

    private var networkService:NetworkService?=null
    private var feedAdapter:FeedAdapter?=null
    private var requestManager:RequestManager?=null
    private var feedList:ArrayList<FeedList>?=null
    private var scrapRecyclerView:RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_scrap,container,false) //레이아웃을 붙이는 과정
        networkService=ApplicationController.instance!!.networkService

        requestManager=Glide.with(this.activity)

        scrapRecyclerView=v.findViewById(R.id.feed_recyclerview_scrap)
        scrapRecyclerView!!.isNestedScrollingEnabled=false
        scrapRecyclerView!!.layoutManager= GridLayoutManager(this.context,2) as RecyclerView.LayoutManager?


        if(arguments!=null){
        }

        getFeed(4)

        onActivityCreated(savedInstanceState)
        return v

    }
    fun getToken(key : String) : String{
        val prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }
    fun getFeed(flag:Int?){
        var getFeedResponse=networkService!!.getFeed(getToken("token"),GetFeedPost(flag!!))
        getFeedResponse.enqueue(object : Callback<GetFeedResponse> {
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                Log.d("슈바",flag.toString())
                Log.d("토큰",getToken("token"))

                if (response!!.isSuccessful){
                    if(response.body().status==200){
                        Log.d("슈바!!",response.body().status.toString())
                        feedAdapter= FeedAdapter(response.body().result,requestManager!!)
                        feedList = response.body().result
                        feedAdapter!!.setOnItemClickListener(this@ScrapFragment)
                        scrapRecyclerView!!.adapter=feedAdapter
                    }
                }
            }

            override fun onFailure(call: Call<GetFeedResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })

    }


    override fun onClick(v: View?) {
        val idx : Int = scrapRecyclerView!!.getChildAdapterPosition(v)
        val feedType : Int = 2
        //val name : String? = postDatas!!.get(idx).pocketmonName
        //val type : String? = pocketmonDatas!!.get(idx).pocketmonType
        //val pro : Int? = pocketmonDatas!!.get(idx).pocketmonImage

        val intent = Intent(activity, MyfeedBigActivity::class.java)

        intent.putExtra("feedType", feedType)
        intent.putExtra("updated", feedList!!.get(idx).updated)
        intent.putExtra("like_count",feedList!!.get(idx).like_count)
        intent.putExtra("comment_count",feedList!!.get(idx).comment_count)
        intent.putExtra("scrap_count",feedList!!.get(idx).scrap_count)
        intent.putExtra("nickname",feedList!!.get(idx).nickname)
        //intent.putExtra("type", type)
        //intent.putExtra("name", name)
        startActivity(intent)


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}