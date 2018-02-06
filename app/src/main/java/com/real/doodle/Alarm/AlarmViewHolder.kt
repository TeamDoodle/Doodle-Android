package com.real.doodle.Alarm

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.real.doodle.R

/**
 * Created by Dayoung on 2018-01-01.
 */
class AlarmViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var description: TextView =itemView!!.findViewById(R.id.alarm_description)
    var userName: TextView =itemView!!.findViewById(R.id.alarm_name)
    var allLay: LinearLayout = itemView!!.findViewById(R.id.alarm_all_lay)
    var userImage: ImageView=itemView!!.findViewById(R.id.alarm_img)
}