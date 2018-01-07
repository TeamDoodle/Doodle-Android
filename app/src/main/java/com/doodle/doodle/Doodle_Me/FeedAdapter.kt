package com.doodle.doodle.Doodle_Me

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.R

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class FeedAdapter(var feedList : ArrayList<FeedList>, var requestManager: RequestManager) : RecyclerView.Adapter<Myfeed_ViewHolder>() {
    override fun onBindViewHolder(holder: Myfeed_ViewHolder?, position: Int) {
        Log.d("onBindViewHolder","onBindViewHolder")
       getItemViewType(position)
        if(feedList!!.get(position).image==null){
            requestManager!!.load(R.drawable.pa).into(holder!!.postImage)
        }else{
            requestManager!!.load(feedList!!.get(position).image).into(holder!!.postImage)
        }

    }

    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = feedList!!.size


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Myfeed_ViewHolder? {

        Log.v("209", "2092")
        val mainView : View= LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.feed_item, parent, false) //레이아웃 연결
            mainView.setOnClickListener(onItemClick)
            return Myfeed_ViewHolder(mainView!!)

    }



}