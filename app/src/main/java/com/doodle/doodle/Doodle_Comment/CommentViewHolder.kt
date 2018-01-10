package com.doodle.doodle.Doodle_Comment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.doodle.doodle.R

/**
 * Created by Administrator on 2018-01-05.
 */
class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var commentImage : ImageView =itemView!!.findViewById(R.id.comment_item_image)
    var commentNickname : TextView=itemView!!.findViewById(R.id.comment_item_nickname)
    var commentComment : TextView =itemView!!.findViewById(R.id.comment_item_comment)

}