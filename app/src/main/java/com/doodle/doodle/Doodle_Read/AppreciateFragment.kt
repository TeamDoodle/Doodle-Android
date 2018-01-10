package com.doodle.doodle.Doodle_Read

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Me.CommonFeedData
import com.doodle.doodle.Login.CommonData
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.appreciate_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by SAMSUNG on 2018-01-05.
 */
class AppreciateFragment : Fragment(), View.OnClickListener {

    private var networkService:NetworkService?=null
    private var weekRecyclerview : RecyclerView? = null
    private var weekList : ArrayList<FeedList>? = null
    private var adapter : AppreciateAdapter? = null
    private var requestManger:RequestManager?=null
    private var appreciate_flag:Int?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_appreciate,container,false) //레이아웃을 붙이는 과정
        networkService=ApplicationController.instance!!.networkService
        appreciate_flag= arguments.getInt("flag")
        requestManger=Glide.with(this)
        weekRecyclerview = v.findViewById(R.id.appreciate_recyclerview)
        getFeed(appreciate_flag!!)
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
        Log.d("flag",flag.toString())
        var getFeedResponse=networkService!!.getFeed(getToken("token"), GetFeedPost(flag!!))
        getFeedResponse.enqueue(object : Callback<GetFeedResponse> {
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                if (response!!.isSuccessful){
                    if(response.body().status==200){
                        weekList = response.body().result
                        CommonFeedData.feedList=response.body().result
                        adapter= AppreciateAdapter(activity,weekList!!,requestManger!!)
                        adapter!!.setOnItemClickListener(this@AppreciateFragment)

                        weekRecyclerview!!.adapter=adapter

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
        val idx : Int = weekRecyclerview!!.getChildAdapterPosition(v)
        val feedType : Int = 1
        Log.d("ㅇ러니아러니ㅏ어졸려","ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ")
        when(v){
            like->{
                Log.d("a","aa")
            }
        }
    }


}
