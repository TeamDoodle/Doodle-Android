package com.doodle.doodle.Doodle_Read

/**
 * Created by Jinyoung on 2018-01-04.
 */
data class  FeedList(
        var nickname:String,
        var idx:Int,
        var text:String,
        var image:String?,
        var size:Int,
        var lineSpacing:Int,
        var font:String,
        var color:String,
        var comment_count:Int,
        var scrap_count:Int,
        var like_count:Int,
        var created:String,
        var updated:String,
        var user_idx:Int,
        var scraps:Int,
        var like:Int
)