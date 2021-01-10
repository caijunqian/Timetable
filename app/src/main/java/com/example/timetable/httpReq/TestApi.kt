package com.example.timetable.httpReq

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestApi {
    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
//            testCourseApi()
//            testItem()
        }
        private fun testItem(){
            /*
            val api = RetrofitUtils.getRetrofit().create(ItemApi::class.java)
            api.deleteItem(8)
                .enqueue(object :Callback<ItemBean>{
                    override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                        println("fail")
                    }
                })

            api.insertItem(1,"测试事件","这是一条测试事件","2021-1-12",1)
                .enqueue(object :Callback<ItemBean>{
                    override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                        println("fail")
                    }
                })


            api.selectItems(1,0)
                .enqueue(object :Callback<ItemBean>{
                    override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                        println("fail")
                    }

                })

            api.selectDeadlineItems(1)
                .enqueue(object :Callback<ItemBean>{
                    override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                        println("fail")
                    }

                })

             */
        }
        private fun testCourseApi(){
            val api = RetrofitUtils.getRetrofit().create(CourseApi::class.java)
            api.getCurCourseOfWeek(1)
                .enqueue(object : Callback<CourseBean> {
                    override fun onResponse(
                        call: Call<CourseBean>,
                        response: Response<CourseBean>
                    ) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<CourseBean>, t: Throwable) {
                    }
                })
            api.getCourseOfWeek(1,8)
                .enqueue(object : Callback<CourseBean> {
                    override fun onResponse(call: Call<CourseBean>, response: Response<CourseBean>) {
                        response?.let { it ->
                            it.body()?.let {
                                println(""+it.code+it.msg+it.data)
                            }
                        }
                    }
                    override fun onFailure(call: Call<CourseBean>, t: Throwable) {
                    }
                })
        }
        private fun testInfoApi(){
            val api = RetrofitUtils.getRetrofit().create(InfoApi::class.java)
            api.verifyInfo(num="123456",pwd = "123")
                .enqueue(object : Callback<InfoBean> {
                    override fun onResponse(call: Call<InfoBean>, response: Response<InfoBean>) {
                        response?.let { it ->
                            it.body()?.let {
                            }
                        }
                    }
                    override fun onFailure(call: Call<InfoBean>, t: Throwable) {
                    }
                })


            api.updatePwd("1","111","123")
                .enqueue(object : Callback<InfoBean> {
                    override fun onResponse(call: Call<InfoBean>, response: Response<InfoBean>) {
                        response?.let { it ->
                            it.body()?.let {
                            }
                        }
                    }
                    override fun onFailure(call: Call<InfoBean>, t: Throwable) {
                    }
                })
        }
    }
}