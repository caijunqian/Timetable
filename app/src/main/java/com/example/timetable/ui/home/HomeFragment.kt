package com.example.timetable.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.GlobalMsg
import com.example.timetable.R
import com.example.timetable.Subject
import com.example.timetable.SubjectRepertory
import com.example.timetable.ui.Login
import com.example.timetable.ui.PasswordActivity
import com.zhuangfei.timetable.TimetableView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val homeTvNum = root.findViewById<TextView>(R.id.homeTvNum)
        val homeTvName = root.findViewById<TextView>(R.id.homeTvName)
        val homeTvGrade = root.findViewById<TextView>(R.id.homeTvGrade)
        val homeTvClass = root.findViewById<TextView>(R.id.homeTvClass)
        homeTvNum.text="学号："+GlobalMsg.info.num
        homeTvName.text="姓名："+GlobalMsg.info.name
        homeTvGrade.text="年级："+GlobalMsg.info.grade
        homeTvClass.text="班级："+GlobalMsg.info.classes
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogout = view.findViewById<TextView>(R.id.homeBtnLogout)
        val btnUpdate = view.findViewById<TextView>(R.id.homeBtnUpdate)
        btnLogout.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Login::class.java)
                //退出登录后，栈中的所有activity都会清空，然后跳转到LoginActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                it.startActivity(intent)
            }
        }
        btnUpdate.setOnClickListener {
            activity?.let {
                val intent = Intent(it, PasswordActivity::class.java)
                it.startActivity(intent)
                print("跳转到修改密码的fragment")
            }
        }
    }


}