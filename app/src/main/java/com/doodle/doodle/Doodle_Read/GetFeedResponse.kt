package com.doodle.doodle.Doodle_Read

/**
 * Created by Jinyoung on 2018-01-04.
 */
data class GetFeedResponse(
        var status: Int,
        var message: String,
        var result: ArrayList<FeedList>
)