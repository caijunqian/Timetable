package com.example.timetable

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zhuangfei.timetable.TimetableView


class MainActivity : AppCompatActivity(){
    private var mTimetableView: TimetableView? = null
    private var mySubjects:List<Subject>? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTimetableView()

        requestData()
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private fun requestData() {
        alertDialog = AlertDialog.Builder(this)
            .setMessage("模拟请求网络中..")
            .setTitle("Tips").create()
        alertDialog?.show()
        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            handler.sendEmptyMessage(0x123)
        }.start()
    }
    private var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (alertDialog != null) alertDialog!!.hide()
            mySubjects = SubjectRepertory.loadDefaultSubjects1()
            Log.d("jj", mySubjects.toString());
            mTimetableView!!.source(mySubjects).showView()
        }
    }

    private fun initTimetableView() {

        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView)
        mTimetableView!!.maxSlideItem(11).itemHeight(65)
        mTimetableView!!.showView()
    }




}