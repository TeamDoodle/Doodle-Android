package com.real.doodle.Doodle_Search

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Me.FeedFragment
import com.real.doodle.Doodle_Me.RecyclerViewItemDeco
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dayoung on 2018-01-07.
 */

class SearchWordingFragment : Fragment() {

    var recyclerView : RecyclerView ?= null
    var searchWordingAdapter : SearchWordingAdapter ?= null
    var searchData : ArrayList<SearchWordingData> ?=null
    var layoutManager:StaggeredGridLayoutManager ? =null
    private var networkService: NetworkService?=null
    var requestManager: RequestManager?=null
    var pref: SharedPreferences? = null
    var token: String? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = layoutInflater.inflate(R.layout.activity_search_wording_list, container, false)

        recyclerView = v.findViewById(R.id.search_wording_recycler)
        searchData =ArrayList<SearchWordingData>()

        //사이즈에 맞게 자동조절
        recyclerView!!.setHasFixedSize(true)


        layoutManager= StaggeredGridLayoutManager(2,1)
        layoutManager!!.gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        layoutManager!!.orientation=StaggeredGridLayoutManager.VERTICAL
        recyclerView!!.layoutManager= layoutManager
        recyclerView!!.addItemDecoration(RecyclerViewItemDeco(2,convertDpToPixel(4,this),true))

        recyclerView!!.adapter = searchWordingAdapter

        return v
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
        }
        super.onCreate(savedInstanceState)

        SearchData.searchWordingFragment = this

        networkService = ApplicationController.instance!!.networkService
        searchData = ArrayList<SearchWordingData>()
        networkService = ApplicationController.instance!!.networkService


//        // 토큰 가져오기
        pref = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token","")



        requestManager = Glide.with(this)
        searchWordingAdapter = SearchWordingAdapter(activity,searchData!!, requestManager!!)


    }

    fun networking(keyword: String?){
        var getNameSearch = networkService!!.getWording(token!!, keyword!!)
        getNameSearch.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
                if (response!!.isSuccessful) {
                    if (response.body().status == 200) {
                        searchWordingAdapter!!.setAdapter(response.body().result)
                        recyclerView!!.adapter = searchWordingAdapter


                    } else {
                        ApplicationController.instance!!.makeToast("검색실패")
                    }
                }
            }


            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })


    }
    fun convertDpToPixel(dp: Int, context: SearchWordingFragment):Float{

        val resources: Resources = context.resources

        val metrics: DisplayMetrics = resources.displayMetrics

        val px:Float = dp * (metrics.densityDpi / 160f);

        return px

    }

}