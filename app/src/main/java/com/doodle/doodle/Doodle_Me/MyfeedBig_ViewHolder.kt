package com.doodle.doodle.Doodle_Me

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R

/**
 * Created by SAMSUNG on 2018-01-02.
 */
class MyfeedBig_ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var postBigImage : ImageView = itemView!!.findViewById(R.id.feedBigImage)
    var PostDate : TextView = itemView!!.findViewById(R.id.postDate)
    var LikeHit : TextView = itemView!!.findViewById(R.id.likeHit)
    var CommentHit : TextView = itemView!!.findViewById(R.id.commentHit)
    var ShareHit : TextView = itemView!!.findViewById(R.id.shareHit)

}