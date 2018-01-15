package com.doodle.doodle.Doodle_Me

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.R

/**
 * Created by Jinyoung on 2018-01-07.
 */
class FeedAdapter(var con:Context, var feedList:ArrayList<FeedList>, var requestManager:RequestManager): RecyclerView.Adapter<FeedViewHolder>() {
    var pos:Int?=null
    var flag:Int?=null
    fun setAdapter(feedList: ArrayList<FeedList>,flag:Int){
        this.feedList = feedList
        this.flag=flag
    }
    override fun onBindViewHolder(holder: FeedViewHolder?, position: Int) {
        if(feedList!!.get(position).image==null){
            requestManager!!.load(R.drawable.mytext1).into(holder!!.postImage)
        }else{
            requestManager!!.load(feedList!!.get(position).image).into(holder!!.postImage)
        }
            holder!!.postImage.setOnClickListener {

                val intent:Intent= Intent(con,MyfeedBigActivity::class.java)
               pos=holder!!.adapterPosition
                intent.putExtra("flag",flag!!)
                intent.putExtra("position",pos!!)

                con.startActivity(intent)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder? {
        val mainView:View=LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.feed_item,parent,false)
                mainView.setOnClickListener(onItemClick)

        return FeedViewHolder(mainView!!)
    }

    override fun getItemCount(): Int =feedList!!.size



    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

}