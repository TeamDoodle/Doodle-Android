package com.real.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Read.FeedList
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class BigGatherFragment: Fragment(), View.OnClickListener {

    private var BigFeedRecyclerview : RecyclerView? = null
    private var bigList : ArrayList<FeedList>? = null
    private var bigAdapter : FeedBigAdapter? = null
    private var position:Int?=null
    private var flag:Int?=null
    private var requestManager: RequestManager?=null
    private var networkService:NetworkService?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)
        networkService=ApplicationController.instance!!.networkService
        val v = inflater!!.inflate(R.layout.fragment_big_gather,container,false) //레이아웃을 붙이는 과정
        if(arguments!=null){

        }
//        idx=arguments.getInt("idx")
        flag=arguments.getInt("flag")
        position=arguments.getInt("position")

        BigFeedRecyclerview = v.findViewById(R.id.feed_big_recyclerview)
        BigFeedRecyclerview!!.scrollToPosition(position!!)
        bigList = ArrayList<FeedList>()
        setFeed(flag)
        requestManager= Glide.with(this)
        bigAdapter = FeedBigAdapter(activity,bigList!!, requestManager!!)
        bigAdapter!!.setOnItemClickListener(this)
        BigFeedRecyclerview!!.adapter = bigAdapter
        bigAdapter!!.setAdapter(flag!!,bigList!!)
        Log.d("나의 글 3/담은 글 4=>",flag!!.toString())
        return v

    }


    override fun onClick(v: View?) {
        val idx : Int = BigFeedRecyclerview!!.getChildAdapterPosition(v)
        Log.d("item idx", bigList!!.get(idx).idx.toString())
    }


    fun setFeed(flag:Int?){
//        나의 글
        if(flag==3){
            bigList=CommonFeedData.feedList!!
            CommonFeedData.flag=flag
        }
//        담은 글
        else if(flag==4){
            bigList=CommonScrapData.feedList!!
            CommonScrapData.flag=flag
        }
    }

}