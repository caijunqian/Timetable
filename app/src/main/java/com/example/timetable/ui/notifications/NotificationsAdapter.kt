package com.example.timetable.ui.notifications

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Presentation
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.R
import com.example.timetable.httpReq.ItemBean
import kotlinx.android.synthetic.main.cell.view.*
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(){
//    var allEntrys :List<Entry>?=null
//    var dao:EntryDao ?=null
    var notifications:List<ItemBean.Data>?=null
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var textViewPos:TextView?=itemView.findViewById(R.id.textViewPos)
        var textViewEntry:TextView?=itemView.findViewById(R.id.textViewEntry)
        var textViewDate:TextView?=itemView.findViewById(R.id.textViewDate)
        var imageView:ImageView?=itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater= LayoutInflater.from(parent.context);
        val itemView:View=layoutInflater.inflate(R.layout.cell,parent,false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notice = notifications?.get(position)
        holder.textViewPos!!.text=(position+1).toString()
//        val sdf1 = SimpleDateFormat("MMM d, yyyy, K:m:s a") //格式化从服务器获取的日期
        val sdf2 = SimpleDateFormat("yyyy-MM-dd") //将得到的Date格式化输出
        if (notice != null) {
//            var dateStr = notice.endTime?.substring(0,notice.endTime!!.length-13)
//            Log.d("TAG", "onBindViewHolder: $dateStr ")
            val date =  Date(notice.endTime)
            val endTime = sdf2.format(date)
            holder.textViewEntry!!.text= notice.name
            holder.textViewDate!!.text= endTime
            val drawable = holder.imageView!!.resources.getDrawable(R.drawable.ic_baseline_warning_24)
            if(notice.canDelete!=1) holder.imageView!!.setImageBitmap(drawable.toBitmap())
            else{ //可以删除，设置长按监听
                holder.itemView.setOnLongClickListener {
                    AlertDialog.Builder(holder.itemView.context).setTitle("提示").setMessage("您确定删除该事件吗？")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { _, _ ->
                            if (notice.itemId != null) {
                                //todo 发网络请求进行删除
                            }
                            //todo 删除完进行更新视图并通知
                            this.notifyDataSetChanged()
                        })
                        .setNegativeButton("取消", null).show()
                    return@setOnLongClickListener true
                }
            }
            //设置点击查看详情监听
            holder.itemView.setOnClickListener {
                AlertDialog.Builder(holder.itemView.context).setTitle(notice.name).setMessage(notice.detail)
                    .setPositiveButton("返回", DialogInterface.OnClickListener { _, _ ->
                    })
                    .setNegativeButton("忽略") { _, _ ->
                        Log.d("TAG", "onBindViewHolder: 忽略")
                        //TODO 设置不提醒
                    }.show()
            }
        }
    }

    override fun getItemCount(): Int {
        if(notifications==null)return 0
        return notifications!!.size
    }
}