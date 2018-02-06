package com.real.doodle.Doodle_Comment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.real.doodle.R

/**
 * Created by Administrator on 2018-01-05.
 */
class CommentAdapter(var commentList: ArrayList<CommentList> , var requestManager : RequestManager) : RecyclerView.Adapter<CommentViewHolder>() {

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: CommentViewHolder?, position: Int) {
        if(commentList!!.get(position).profile==null){
            requestManager!!.load(R.drawable.mytext1).placeholder(R.mipmap.ic_launcher).into(holder!!.commentImage)
        }else{
            requestManager!!.load(commentList!!.get(position).profile).placeholder(R.mipmap.ic_launcher).into(holder!!.commentImage)
        }
        holder!!.commentNickname.text = commentList[position].nickname

        holder!!.commentComment.text = commentList[position].content
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentViewHolder {
        val mainview: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(mainview)
    }
}