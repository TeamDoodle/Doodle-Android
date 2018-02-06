package com.real.doodle.Doodle_Search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Others.OtherActivity
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Dayoung on 2018-01-07.
 */
class SearchNameFragment : Fragment(),View.OnClickListener {
    override fun onClick(v: View?) {
    val position=recyclerView!!.getChildAdapterPosition(v)
        val intent:Intent= Intent(activity,OtherActivity::class.java)
    Toast.makeText(activity,"ㅎ이하이하이하ㅣㅏ히앟",Toast.LENGTH_SHORT).show()
    }

    var recyclerView: RecyclerView? = null
    var searchNameAdapter: SearchNameAdapter? = null
    var searchData: ArrayList<SearchNameData>? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var networkService: NetworkService? = null
    var requestManager: RequestManager? = null
    var pref: SharedPreferences? = null
    var token: String? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = layoutInflater.inflate(R.layout.activity_search_name_list, container, false)

        recyclerView = v.findViewById(R.id.search_name_recycler)
        searchData = ArrayList<SearchNameData>()

        // 토큰 가져오기
        pref = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token", "")

        //사이즈에 맞게 자동조절
        recyclerView!!.setHasFixedSize(true)


        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager

        requestManager = Glide.with(this)
        searchNameAdapter = SearchNameAdapter(activity,searchData!!, requestManager!!)
        recyclerView!!.adapter = searchNameAdapter

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
        }
        super.onCreate(savedInstanceState)

        SearchData.searchNameFragment = this

        networkService = ApplicationController.instance!!.networkService
        searchData = ArrayList<SearchNameData>()
        networkService = ApplicationController.instance!!.networkService


//        // 토큰 가져오기
        pref = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token","")



        requestManager = Glide.with(this)
        searchNameAdapter = SearchNameAdapter(activity,searchData!!, requestManager!!)


    }

    public fun networking(keyword: String?) {
        var getNameSearch = networkService!!.getNameSearch(token!!, keyword!!)
        getNameSearch.enqueue(object : Callback<SearchNameResponse> {
            override fun onResponse(call: Call<SearchNameResponse>?, response: Response<SearchNameResponse>?) {
                if (response!!.isSuccessful) {
                    if (response.body().status == 200) {
                        searchNameAdapter!!.setAdapter(response.body().result)
                        recyclerView!!.adapter = searchNameAdapter
                        searchNameAdapter!!.setOnItemClickListener(this@SearchNameFragment)

                    } else {
                        ApplicationController.instance!!.makeToast("검색실패")
                    }
                }
            }


            override fun onFailure(call: Call<SearchNameResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })
    }

}