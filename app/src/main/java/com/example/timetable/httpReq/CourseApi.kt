package com.example.timetable.httpReq

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseApi {
    //获取当周课表
    @GET("getCourseOfWeek/{userId}")
    fun getCurCourseOfWeek(@Path("userId") userId:Int):Call<CourseBean>
    //获取特定某一周的课表
    @GET("getCourseOfWeek/{userId}/{week}")
    fun getCourseOfWeek(@Path("userId") userId:Int,@Path("week")week:Int):Call<CourseBean>
}