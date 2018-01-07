package com.doodle.doodle.Login

/**
 * Created by Jinyoung on 2018-01-03.
 */
data class LoginResponse (
    var status:Int,
    var  message:String,
    var result:LoginData
)