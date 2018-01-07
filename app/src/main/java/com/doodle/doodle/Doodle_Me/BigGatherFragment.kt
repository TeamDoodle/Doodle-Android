package com.doodle.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.R

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class BigGatherFragment: Fragment(), View.OnClickListener {

    private var BigFeedList : RecyclerView? = null
    private var postDatas : ArrayList<FeedBigList>? = null
    private var adapter : FeedBigAdapter? = null
    private var nameTag : String = ""
    private var idx:Int?=null
    private var updated:String?=null
    private var like_count:Int?=null
    private var comment_count:Int?=null
    private var scrap_count:Int?=null
    private var nickname:String?=null
    private var image:String?=null
    private var requestManager: RequestManager?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)

        val v = inflater!!.inflate(R.layout.fragment_big_gather,container,false) //레이아웃을 붙이는 과정
        if(arguments!=null){
            nameTag = arguments.get("page").toString()
            idx=arguments.getInt("idx")
            updated=arguments.get("updated").toString()
            like_count=arguments.getInt("like_count")
            comment_count=arguments.getInt("comment_count")
            scrap_count=arguments.getInt("scrap_count")
            nickname=arguments.getString("nickname").toString()
            image=arguments.getString("image").toString()
        }

        BigFeedList = v.findViewById(R.id.feed_big_recyclerview)

        postDatas = ArrayList<FeedBigList>()
        postDatas!!.add(FeedBigList(updated!!,like_count!!,comment_count!!,scrap_count!!,
                                    nickname!!,idx!!,image!!))
        requestManager= Glide.with(this)
        adapter = FeedBigAdapter(postDatas!!, requestManager!!,nameTag, "")
        adapter!!.setOnItemClickListener(this)
        BigFeedList!!.adapter = adapter


        return v

    }

    override fun onClick(v: View?) {
        val idx : Int = BigFeedList!!.getChildAdapterPosition(v)



    }


}