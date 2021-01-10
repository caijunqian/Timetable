package com.example.timetable.httpReq

import retrofit2.Call
import retrofit2.http.*

interface InfoApi {
    //put请求实例
    @FormUrlEncoded
    @PUT("info/updatePwd")
    fun updatePwd(@Field("userId") num:String, @Field("oldPwd") oldPwd:String
            ,@Field("newPwd") newPwd:String): Call<InfoBean>

    //post请求实例
    @FormUrlEncoded
    @POST("info/verify")
    fun verifyInfo(@Field("num") num:String, @Field("pwd") pwd:String): Call<InfoBean>
}