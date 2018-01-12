package com.doodle.doodle.Doodle_Others

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R

/**
 * Created by Jinyoung on 2018-01-11.
 */
class BigOtherViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
    var BigOtherImage:ImageView=itemView!!.findViewById(R.id.big_other_image)
    var BigOtherDate:TextView=itemView!!.findViewById(R.id.big_other_date)
    var BigOtherLike:TextView=itemView!!.findViewById(R.id.big_other_like)
    var BigOtherLikeCount:TextView=itemView!!.findViewById(R.id.big_other_likeCount)
    var BigOtherComment:TextView=itemView!!.findViewById(R.id.big_other_comment)
    var BigOtherCommentCount:TextView=itemView!!.findViewById(R.id.big_other_commentCount)
    var BigOtherScrap:TextView=itemView!!.findViewById(R.id.big_other_scrap)
    var BigOtherScrapCount:TextView=itemView!!.findViewById(R.id.big_other_scrapCount)
}