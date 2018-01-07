package com.doodle.doodle.Doodle_Read

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R

/**
 * Created by Jinyoung on 2018-01-05.
 */
class AppreciateDayViewHolder(itemView:View?):RecyclerView.ViewHolder(itemView) {
    var image:ImageView=itemView!!.findViewById(R.id.day_image)
    var updated:TextView=itemView!!.findViewById(R.id.today)
    var like:TextView=itemView!!.findViewById(R.id.day_like)
    var comment:TextView=itemView!!.findViewById(R.id.day_comment)
    var scrap:TextView=itemView!!.findViewById(R.id.day_scrap)
    var nickname:TextView=itemView!!.findViewById(R.id.day_nickname)
    var image_setting:ImageView=itemView!!.findViewById(R.id.day_image_setting)
}