package com.doodle.doodle.Doodle_Me

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.R
class FeedBigAdapter(var postList : ArrayList<FeedBigList>,
                     var requestManager: RequestManager,
                     var nameTag : String,
                     var dayTag : String) : RecyclerView.Adapter<MyfeedBig_ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyfeedBig_ViewHolder? {


        val mainView : View= LayoutInflater.from(parent!!.context)
                .inflate(R.layout.feed_big_item, parent, false) //레이아웃 연결
        mainView.setOnClickListener(onItemClick)
        return MyfeedBig_ViewHolder(mainView)

    }

    override fun onBindViewHolder(holder: MyfeedBig_ViewHolder?, position: Int) {
        if(postList!!.get(position).image==null){
            requestManager!!.load(R.drawable.pa).into(holder!!.postBigImage)
        }else{
            requestManager!!.load(postList!!.get(position).image).into(holder!!.postBigImage)
        }
        if(dayTag == "day")
            holder!!.PostDate.visibility = View.GONE
        else
            holder!!.PostDate.setText(postList!!.get(position).updated)
        holder!!.LikeHit.setText(postList!!.get(position).like_count.toString())
        holder!!.CommentHit.setText(postList!!.get(position).comment_count.toString())
        holder!!.ShareHit.setText(postList!!.get(position).scrap_count.toString())
        if(nameTag == "first")
            holder!!.WritingName.visibility = View.GONE
        else
            holder!!.WritingName.setText(postList!!.get(position).nickname.toString())
    }


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }


    override fun getItemCount(): Int = postList!!.size






}