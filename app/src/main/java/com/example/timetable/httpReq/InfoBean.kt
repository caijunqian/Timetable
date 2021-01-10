package com.example.timetable.httpReq
import com.google.gson.annotations.SerializedName


data class InfoBean(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("msg")
    var msg: String?
){
    data class Data(
        @SerializedName("classes")
        var classes: String?,
        @SerializedName("grade")
        var grade: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("num")
        var num: String?,
        @SerializedName("pwd")
        var pwd: String?,
        @SerializedName("userId")
        var userId: Int?
    )
}

