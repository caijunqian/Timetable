package com.example.timetable.httpReq

import retrofit2.Call
import retrofit2.http.*

//日期是字符串的，还没进行处理
interface ItemApi {
    @GET("items/{userId}/{isFinished}")
    fun selectItems(@Path("userId") userId:Int,@Path("isFinished") isFinished:Int):Call<ItemBean>

    @GET("expiringItems/{userId}")
    fun selectDeadlineItems(@Path("userId") userId: Int):Call<ItemBean>

    @DELETE("item/{itemId}")
    fun deleteItem(@Path("itemId") itemId:Int):Call<ItemBean>

    //注意日期格式为yyyy-MM-dd
    @POST("item")
    @FormUrlEncoded
    fun insertItem(@Field("userId") userId:Int,@Field("name")name:String,@Field("detail")detail:String
                   ,@Field("endTime")endTime:String,@Field("canDelete")canDelete:Int):Call<ItemBean>
}