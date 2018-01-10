package com.doodle.doodle.Doodle_Me

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R
import org.w3c.dom.Text

/**
 * Created by SAMSUNG on 2018-01-02.
 */
class BigFeedViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var postBigImage : ImageView = itemView!!.findViewById(R.id.feedBigImage)
    var PostDate : TextView = itemView!!.findViewById(R.id.postDate)
    var WritingName:TextView=itemView!!.findViewById(R.id.big_nickname)
    var imageSetting:ImageView=itemView!!.findViewById(R.id.image_setting)
    var bigLike:TextView=itemView!!.findViewById(R.id.big_like)
    var bigLike_count:TextView=itemView!!.findViewById(R.id.big_likeCount)
    var bigComment:TextView=itemView!!.findViewById(R.id.big_comment)
    var bigComment_count:TextView=itemView!!.findViewById(R.id.big_commentCount)
    var bigScrap:TextView=itemView!!.findViewById(R.id.big_scrap)
    var bigScrap_count:TextView=itemView!!.findViewById(R.id.big_scrapCount)
    var bigNickname:TextView=itemView!!.findViewById(R.id.big_nickname)
}