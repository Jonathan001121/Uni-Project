package com.example.webbasedsystem

data class LoginModel(
    val firstname: String,
    val last_name: String,
    val token:String
)
class ViewLoginModel(){

    companion object {
        var firstname: String=""
        var last_name: String=""
        var token:String=""
    }

}