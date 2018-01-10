package com.doodle.doodle.Doodle_Comment

/**
 * Created by SAMSUNG on 2018-01-08.
 */
data class CommentList (

        var idx:Int,
        var content:String,
        var created:String,
        var updated:String,
        var user_idx:Int,
        var doodle_idx:Int,
        var nickname:String,
        var profile : String)
