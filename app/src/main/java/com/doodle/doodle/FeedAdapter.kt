package com.doodle.doodle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class FeedAdapter(var postList : ArrayList<FeedList>) : RecyclerView.Adapter<Myfeed_ViewHolder>() {



    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }


    override fun onBindViewHolder(holder: Myfeed_ViewHolder?, position: Int) {
        holder!!.postImage.setImageResource(postList!!.get(position).feedImage)

    }
//
    override fun getItemCount(): Int = postList!!.size



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Myfeed_ViewHolder? {

            val mainView : View= LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.feed_item, parent, false) //레이아웃 연결
            mainView.setOnClickListener(onItemClick)
            return Myfeed_ViewHolder(mainView)

    }



}