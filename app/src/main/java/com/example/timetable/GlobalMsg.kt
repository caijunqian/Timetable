package com.example.timetable

import com.example.timetable.httpReq.InfoBean

/***
 * 全局类，用于保存全局变量
 */
class GlobalMsg {
    companion object{
        lateinit var info: InfoBean.Data;   //全局变量，用户登录后把用户信息保存到这里
    }
}