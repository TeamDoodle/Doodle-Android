package com.doodle.doodle.Doodle_Read
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Me.FeedBigAdapter
import com.doodle.doodle.Doodle_Me.FeedBigList
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("ValidFragment")
/**
 * Created by SAMSUNG on 2018-01-04.
 */
//일일 감상 Fragment
class AppreciateDayFragment: Fragment, View.OnClickListener {

    private var networkService:NetworkService?=null
    private var dayRecyclerview : RecyclerView? = null
    private var dayList:ArrayList<FeedList>?=null
    private var adapter : AppreciateDayAdapter? = null
    private var nameTag : String = ""
    private var dayTag : String = ""
    private var requestManager:RequestManager?=null

    constructor(dayTag : String) : super(){
        this.dayTag = dayTag
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_appreciate,container,false) //레이아웃을 붙이는 과정
        networkService=ApplicationController.instance!!.networkService
        getFeed(2)
        requestManager= Glide.with(this)
        dayRecyclerview=v.findViewById(R.id.appreciate_recyclerview)
        if(arguments!=null){
            nameTag = arguments.get("page").toString()
            //v!!.first_text.text = arguments.getString("title")
        }

        return v

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getToken(key : String) : String{
        val prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }

    fun getFeed(flag:Int?){
        var getFeedResponse=networkService!!.getFeed(getToken("token"), GetFeedPost(flag!!))
        getFeedResponse.enqueue(object : Callback<GetFeedResponse> {
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                if (response!!.isSuccessful){
                    if(response.body().status==200){
                        dayList = response.body().result
//                        feedAdapter!!.notifyDataSetChanged()
                        Log.d("슈발","슈발")
                        adapter= AppreciateDayAdapter(dayList!!,requestManager!!)
                        adapter!!.setOnItemClickListener(this@AppreciateDayFragment)

                        dayRecyclerview!!.adapter=adapter

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
        val idx : Int = dayRecyclerview!!.getChildAdapterPosition(v)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}