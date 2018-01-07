package com.doodle.doodle.Doodle_Read

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Me.Myfeed_ViewHolder
import com.doodle.doodle.R

/**
 * Created by Jinyoung on 2018-01-05.
 */
class AppreciateDayAdapter(var feedList : ArrayList<FeedList>,var requestManager: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        var holder:AppreciateDayViewHolder=holder as AppreciateDayViewHolder
        if(feedList!!.get(position).image==null){
            requestManager!!.load(R.drawable.mytext2).into(holder!!.image)
        }else{
            requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
        }
        holder!!.comment.text=feedList!!.get(position).comment_count.toString()
        holder!!.like.text=feedList!!.get(position).like_count.toString()
        holder!!.scrap.text=feedList!!.get(position).scrap_count.toString()
        holder!!.nickname.text=feedList!!.get(position).nickname
        holder!!.updated.text=feedList!!.get(position).updated
        holder!!.image_setting.setOnClickListener {
            Log.d("일일 감상","슈발")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AppreciateDayViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.day_item, parent, false) //레이아웃 연결
        mainView.setOnClickListener(onItemClick)
        return AppreciateDayViewHolder(mainView)
    }

    override fun getItemCount(): Int =feedList!!.size
    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }
}