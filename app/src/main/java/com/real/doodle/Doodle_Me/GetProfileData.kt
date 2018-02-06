package com.real.doodle.Doodle_Me

/**
 * Created by Jinyoung on 2018-01-13.
 */
data class GetProfileData (
        var idx:Int,
        var nickname:String,
        var description:String,
        var image:String?,
        var doodle_count:Int,
        var scrap_count:Int
)