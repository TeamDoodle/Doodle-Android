package com.doodle.doodle.Doodle_Read

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R

/**
 * Created by SAMSUNG on 2018-01-03.
 */
class AppreciateViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var image:ImageView=itemView!!.findViewById(R.id.appreciate_postImage)
    var likecount:TextView=itemView!!.findViewById(R.id.appreciate_like)
    var commentcount:TextView=itemView!!.findViewById(R.id.appreciate_comment)
    var scrapcount:TextView=itemView!!.findViewById(R.id.appreciate_scrap)
    var nickname:TextView=itemView!!.findViewById(R.id.appreciate_nickname)
    var rankimage:ImageView=itemView!!.findViewById(R.id.appreciate_topRate)
    var like:TextView=itemView!!.findViewById(R.id.like)
    var comment:TextView=itemView!!.findViewById(R.id.comment)
    var scrap:TextView=itemView!!.findViewById(R.id.scrap)
}