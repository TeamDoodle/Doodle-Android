package com.real.doodle.Doodle_Search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.real.doodle.R


/**
 * Created by Dayoung on 2018-01-01.
 */
class SearchNameViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    //TODO userImage 이미지뷰로 바꾸기
    var userImage:ImageView=itemView!!.findViewById(R.id.search_name_item_iv)
    var userName: TextView =itemView!!.findViewById(R.id.search_name_item_tv)

}