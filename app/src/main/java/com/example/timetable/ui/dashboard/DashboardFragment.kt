package com.example.timetable.ui.dashboard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.*
import com.zhuangfei.timetable.TimetableView

class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var dashboardViewModel: DashboardViewModel

    private var mTimetableView: TimetableView? = null
    private var mySubjects:List<Subject>? = null
    private var searchSubjects:List<Subject>? = null
    private var alertDialog: AlertDialog? = null

    private var curWeekBtn :Button?=null
    private var preWeekBtn: Button? = null
    private var nextWeekBtn:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //获取课程表控件
        mTimetableView = root.findViewById(R.id.id_timetableView)
        mTimetableView!!.maxSlideItem(11).itemHeight(59)
        mTimetableView!!.showView()

        //获取按钮控件
        preWeekBtn = root.findViewById(R.id.btn_preWeek)
        preWeekBtn!!.setOnClickListener(this)
        nextWeekBtn = root.findViewById(R.id.btn_nextWeek)
        nextWeekBtn!!.setOnClickListener(this)
        curWeekBtn = root.findViewById(R.id.btn_curWeek)
        curWeekBtn!!.setOnClickListener(this)
        requestData()
        return root
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private fun requestData() {
        alertDialog = AlertDialog.Builder(this.activity)
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_preWeek->{
                Log.d("jj","上一周")
                searchSubjects = SubjectRepertory.loadDefaultSubjects()
                mTimetableView!!.source(searchSubjects).showView()
            }
            R.id.btn_nextWeek->{
                Log.d("jj","下一周")
                searchSubjects = SubjectRepertory.loadDefaultSubjects()
                mTimetableView!!.source(searchSubjects).showView()
            }
            R.id.btn_curWeek->{
                mTimetableView!!.source(mySubjects).showView()


            }
        }

    }


}