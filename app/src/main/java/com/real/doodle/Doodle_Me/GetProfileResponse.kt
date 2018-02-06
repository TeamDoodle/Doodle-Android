package com.real.doodle.Doodle_Me

/**
 * Created by Jinyoung on 2018-01-17.
 */
data class GetProfileResponse (
        var status:Int,
        var message:String,
        var result:GetProfileData
)