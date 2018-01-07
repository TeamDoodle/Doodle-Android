package com.doodle.doodle.Doodle_Read

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.doodle.doodle.R
import com.doodle.doodle.R.id.appreciate_image_setting

class AppreciateAdapter(var feedList : ArrayList<FeedList>,var requestManager: RequestManager) : RecyclerView.Adapter<AppreciateViewHolder>() {
    override fun onBindViewHolder(holder: AppreciateViewHolder?, position: Int) {
        getItemViewType(position)
//        랭킹 붙은 부분일 때
        if(getItemViewType(position)==VIEW_TYPE_TOP_RATE){
            when(position){
                0-> holder!!.rankimage.setImageResource(R.drawable.rank1)
                1-> holder!!.rankimage.setImageResource(R.drawable.rank2)
                2-> holder!!.rankimage.setImageResource(R.drawable.rank3)
            }
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext1).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.comment.text=feedList!!.get(position).comment_count.toString()
            holder!!.like.text=feedList!!.get(position).like_count.toString()
            holder!!.scrap.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname
            holder!!.imagesetting.setOnClickListener {
                Log.d("안녕","슈바ㅏㅏㅏㅏㅏㅏㅏㅏㅏ")
            }
        }
//        랭킹 안 붙은 부분일 때
        else if(getItemViewType(position)==VIEW_TYPE_IMAGE){
            if(feedList!!.get(position).image==null){
                requestManager!!.load(R.drawable.mytext1).into(holder!!.image)
            }else{
                requestManager!!.load(feedList!!.get(position).image).into(holder!!.image)
            }
            holder!!.comment.text=feedList!!.get(position).comment_count.toString()
            holder!!.like.text=feedList!!.get(position).like_count.toString()
            holder!!.scrap.text=feedList!!.get(position).scrap_count.toString()
            holder!!.nickname.text=feedList!!.get(position).nickname
        }

    }

    private val VIEW_TYPE_TOP_RATE : Int = 1
    private val VIEW_TYPE_IMAGE : Int = 2


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    override fun getItemCount(): Int = feedList!!.size

    override fun getItemViewType(position: Int): Int {
        when(position){
            0->{
                return VIEW_TYPE_TOP_RATE
            }
            1->{
                return VIEW_TYPE_TOP_RATE
            }
            2->{
                return VIEW_TYPE_TOP_RATE
            }
            else->{
                return VIEW_TYPE_IMAGE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AppreciateViewHolder? {
        var mainView:View?=null
        when(viewType){
            VIEW_TYPE_TOP_RATE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.top_appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateViewHolder(mainView!!)
            }
            VIEW_TYPE_IMAGE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateViewHolder(mainView!!)
            }
        }
        return AppreciateViewHolder(mainView!!)
    }



}

