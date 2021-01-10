package com.example.timetable.httpReq

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtils {
    companion object{
        fun getRetrofit():Retrofit=
            Retrofit.Builder()
                .baseUrl("http://8.129.29.84:8080/course/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}