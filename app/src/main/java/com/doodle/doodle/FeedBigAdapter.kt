package com.doodle.doodle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class FeedBigAdapter(var postList : ArrayList<FeedBigList>, var nameTag : String) : RecyclerView.Adapter<MyfeedBig_ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyfeedBig_ViewHolder? {


        val mainView : View= LayoutInflater.from(parent!!.context)
                .inflate(R.layout.feed_big_item, parent, false) //레이아웃 연결
        mainView.setOnClickListener(onItemClick)
        return MyfeedBig_ViewHolder(mainView)

    }

    override fun onBindViewHolder(holder: MyfeedBig_ViewHolder?, position: Int) {
        holder!!.postBigImage.setImageResource(postList!!.get(position).postBigImage)
        if(nameTag == "day")
            holder!!.PostDate.visibility = View.GONE
        else
            holder!!.PostDate.setText(postList!!.get(position).postDate)
        holder!!.LikeHit.setText(postList!!.get(position).likeHit.toString())
        holder!!.CommentHit.setText(postList!!.get(position).commentHit.toString())
        holder!!.ShareHit.setText(postList!!.get(position).shareHit.toString())
        if(nameTag == "first")
            holder!!.WritingName.visibility = View.GONE
        else
            holder!!.WritingName.setText(postList!!.get(position).WritingName.toString())
    }


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }


    override fun getItemCount(): Int = postList!!.size






}