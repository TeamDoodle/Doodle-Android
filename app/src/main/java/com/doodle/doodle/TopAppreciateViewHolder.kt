package com.doodle.doodle

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

/**
 * Created by SAMSUNG on 2018-01-03.
 */
class TopAppreciateViewHolder (itemView: View?) : RecyclerView.ViewHolder(itemView){
    var Appreciate_topRate : ImageView = itemView!!.findViewById(R.id.appreciate_topRate)
    var AppreciateTop_postImage : ImageView = itemView!!.findViewById(R.id.appreciateTop_postImage)
}