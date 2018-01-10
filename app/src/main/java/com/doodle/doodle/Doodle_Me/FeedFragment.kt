package com.doodle.doodle.Doodle_Me

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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


class FeedFragment : Fragment(), View.OnClickListener {

    private var networkService: NetworkService?=null
    private var feedRecyclerView: RecyclerView?=null
    private var feedList : ArrayList<FeedList>? = null
    private var feedAdapter : FeedAdapter? = null
    private var requestManager: RequestManager?=null
    private var myfeed_flag:Int?=null
    private var layoutManager:StaggeredGridLayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 }

    override fun onResume() {
        super.onResume()
        feedAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_feed, container, false)

        networkService= ApplicationController.instance!!.networkService
        myfeed_flag=arguments.getInt("flag")
        feedList = ArrayList()
        requestManager= Glide.with(this)
        feedAdapter= FeedAdapter(activity,feedList!!,requestManager!!)
        feedRecyclerView=v.findViewById(R.id.myfeed_recyclerview)

        layoutManager= StaggeredGridLayoutManager(2,1)
        layoutManager!!.gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        layoutManager!!.orientation=StaggeredGridLayoutManager.VERTICAL
        feedRecyclerView!!.isNestedScrollingEnabled = false
        feedRecyclerView!!.layoutManager=layoutManager
        feedRecyclerView!!.addItemDecoration(RecyclerViewItemDeco(2,convertDpToPixel(2,this),true))

        feedRecyclerView!!.adapter=feedAdapter
        getFeed(myfeed_flag!!)

        return v
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
                        feedList = response.body().result
                        if(flag==3){
                            CommonFeedData.feedList=response.body().result
                        }
                        else if(flag==4){
                            CommonScrapData.feedList=response.body().result
                        }

                        feedAdapter= FeedAdapter(activity,feedList!!,requestManager!!)
                        feedRecyclerView!!.adapter=feedAdapter
                        feedAdapter!!.setAdapter(response.body().result,flag!!)
                        feedAdapter!!.setOnItemClickListener(this@FeedFragment)
                        feedAdapter!!.notifyDataSetChanged()


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
        val idx:Int=feedRecyclerView!!.getChildAdapterPosition(v)
        Log.d("tttttttttttttttt","tttttttttttttttt")
    }
    fun convertDpToPixel(dp: Int, context: FeedFragment):Float{

        val resources:Resources = context.resources

        val metrics:DisplayMetrics = resources.displayMetrics

        val px:Float = dp * (metrics.densityDpi / 160f);

        return px

    }



}
