package com.real.doodle.Doodle_Write

/**
 * Created by Jinyoung on 2018-01-11.
 */
data class TodayImageResponse (
        var status:Int,
        var message:String,
        var result:ArrayList<TodayImageData>
)