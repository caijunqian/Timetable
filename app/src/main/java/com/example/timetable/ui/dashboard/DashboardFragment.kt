package com.example.timetable.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.*
import com.example.timetable.httpReq.*
import com.zhuangfei.timetable.TimetableView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var dashboardViewModel: DashboardViewModel

    private var mTimetableView: TimetableView? = null
    private var mySubjects:List<Subject>? = null
    private var alertDialog: AlertDialog? = null

    private var curWeekBtn :Button?=null
    private var preWeekBtn: Button? = null
    private var nextWeekBtn:Button? = null
    //当前周以及查询周
    private var currWeekNum:Int? = 1
    private var selectWeekNum:Int? = 1

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
        mTimetableView!!.curWeek(1).showView()

        //获取按钮控件
        preWeekBtn = root.findViewById(R.id.btn_preWeek)
        preWeekBtn!!.setOnClickListener(this)
        nextWeekBtn = root.findViewById(R.id.btn_nextWeek)
        nextWeekBtn!!.setOnClickListener(this)
        curWeekBtn = root.findViewById(R.id.btn_curWeek)
        curWeekBtn!!.setOnClickListener(this)
        requestData()
//        getCurrCourseInfo()
        return root
    }

    private fun getCurrCourseInfo(){
        //查询当前周的所有课程信息
        val api = RetrofitUtils.getRetrofit().create(CourseApi::class.java)
        api.getCurCourseOfWeek(GlobalMsg.info.userId!!)
            .enqueue(object : Callback<CourseBean> {
                override fun onResponse(call: Call<CourseBean>, response: Response<CourseBean>) {
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
//                                Log.d("jj",it.toString())
                                currWeekNum = it.msg!!.toInt()
//                                Log.d("jj","当前周："+currWeekNum)
                                mySubjects = it.data
//                                Log.d("jj","网络课程"+mySubjects)
                                curWeekBtn!!.text = "当前"+currWeekNum+"周"
                                if (alertDialog != null) alertDialog!!.hide()
                                mTimetableView!!.source(mySubjects).showView()
                            } else if(it.code == 204){
                                currWeekNum = it.msg!!.toInt()
                                curWeekBtn!!.text = "当前"+currWeekNum+"周"
                                mySubjects = listOf()
                                if (alertDialog != null) alertDialog!!.hide()
                                mTimetableView!!.source(mySubjects).showView()
                            }else {
                                Log.d("jj",it.toString())
                                Toast.makeText(activity, "网络请求失败，请稍后再试", Toast.LENGTH_LONG)
                                    .show()

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CourseBean>, t: Throwable) {
                    Toast.makeText(activity, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()

                }
            })

    }

    private fun getCourseInfoByWeekNum(weekNum:Int){
        //查询当前周的所有课程信息
        val api = RetrofitUtils.getRetrofit().create(CourseApi::class.java)
        api.getCourseOfWeek(GlobalMsg.info.userId!!,weekNum)
            .enqueue(object : Callback<CourseBean> {
                override fun onResponse(call: Call<CourseBean>, response: Response<CourseBean>) {
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
                                Log.d("jj",it.toString())
                                currWeekNum = it.msg!!.toInt()
//                                Log.d("jj","查到周为："+it.msg)
                                mySubjects = it.data
//                                Log.d("jj","网络课程"+mySubjects)
                                curWeekBtn!!.text = ""+currWeekNum+"周/回到当前"
                                if (alertDialog != null) alertDialog!!.hide()
                                mTimetableView!!.source(mySubjects).showView()
                            }else if(it.code == 204){
                                currWeekNum = it.msg!!.toInt()
                                curWeekBtn!!.text = ""+currWeekNum+"周/回到当前"
                                if (alertDialog != null) alertDialog!!.hide()
                                mySubjects = listOf()
                                mTimetableView!!.source(mySubjects).showView()

                            }
                            else {
                                Log.d("jj",it.toString())
                                Toast.makeText(activity, "网络请求失败，请稍后再试", Toast.LENGTH_LONG)
                                    .show()

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CourseBean>, t: Throwable) {
                    Toast.makeText(activity, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()

                }
            })
    }

    /**
     * 获取当前周课程
     * 刷新界面，网络请求
     */
    private fun requestData() {
        alertDialog = AlertDialog.Builder(this.activity)
            .setMessage("请求网络中..")
            .setTitle("Tips").create()
        alertDialog?.show()
        getCurrCourseInfo()
    }

    /**
     * 获取某一周的课程
     */
    private fun requestData(weekNum: Int) {
        alertDialog = AlertDialog.Builder(this.activity)
            .setMessage("请求网络中..")
            .setTitle("Tips").create()
        alertDialog?.show()
        getCourseInfoByWeekNum(weekNum)
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_preWeek->{
                Log.d("jj","上一周")
                selectWeekNum = currWeekNum!!-1
                Log.d("jj",selectWeekNum.toString())
                if(selectWeekNum!!<=0){
                    selectWeekNum = 1
                    Toast.makeText(activity, "目前已经是第一周", Toast.LENGTH_LONG).show()
                    return
                }
                requestData(selectWeekNum!!)
            }
            R.id.btn_nextWeek->{
                Log.d("jj","下一周")
                Log.d("jj",selectWeekNum.toString())
                selectWeekNum = currWeekNum!!+1
                requestData(selectWeekNum!!)
            }
            R.id.btn_curWeek->{
                Log.d("jj","本周")
                Log.d("jj",currWeekNum.toString())
                getCurrCourseInfo()
            }
        }
    }

}