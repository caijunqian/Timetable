package com.example.timetable.httpReq
import com.google.gson.annotations.SerializedName


data class ItemBean(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("msg")
    var msg: String?,
    @SerializedName("data")
    var `data`: List<Data>?
){
    data class Data(
        @SerializedName("canDelete")
        var canDelete: Int?,
        @SerializedName("detail")
        var detail: String?,
        @SerializedName("endTime")
        var endTime: String?,
        @SerializedName("isFinished")
        var isFinished: Int?,
        @SerializedName("itemId")
        var itemId: Int?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("userId")
        var userId: Int?
    )
}

