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
import com.example.timetable.GlobalMsg
import com.example.timetable.R
import com.example.timetable.httpReq.ItemApi
import com.example.timetable.httpReq.ItemBean
import com.example.timetable.httpReq.RetrofitUtils
import kotlinx.android.synthetic.main.cell.view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(){
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
            val warnDrawable = holder.imageView!!.resources.getDrawable(R.drawable.ic_baseline_warning_24)
            val deleteDrawable = holder.imageView!!.resources.getDrawable(R.drawable.ic_baseline_delete_24)
            if(notice.canDelete!=1) {
                holder.imageView!!.setImageBitmap(warnDrawable.toBitmap())
                holder.itemView.setOnLongClickListener {
//                    Toast.makeText(it.context, "该通知无法删除", Toast.LENGTH_LONG).show()
                    return@setOnLongClickListener true
                }
            }
            else{ //可以删除，设置长按监听
                holder.imageView!!.setImageBitmap(deleteDrawable.toBitmap())
                holder.itemView.setOnLongClickListener {
                    AlertDialog.Builder(holder.itemView.context).setTitle("Warn：").setMessage("是否确定删除事件：${notice.name}")
                        .setPositiveButton("删除", DialogInterface.OnClickListener { _, _ ->
                            if (notice.itemId != null) {
                                //发网络请求，进行删并更新视图
                                notice.isFinished?.let { it1 ->
                                    NotificationsFragment.mContext.deleteNotice(notice.itemId!!,
                                        it1
                                    )
                                }
                            }
                        })
                        .setNegativeButton("取消", null).show()
                    return@setOnLongClickListener true
                }
            }
            //设置点击查看详情监听
            holder.itemView.setOnClickListener {
                var hintText:String = "忽略提醒"
                if(notice.isFinished==1){
                    hintText="取消忽略"
                }
                AlertDialog.Builder(holder.itemView.context).setTitle(notice.name).setMessage(notice.detail)
                    .setPositiveButton("返回", DialogInterface.OnClickListener { _, _ ->
                    })
                    .setNegativeButton(hintText) { _, _ ->
                        //设置是否忽略提醒
                        if (notice.itemId != null) {
                            //发网络请求并更新视图
                            notice.isFinished?.let { it1 ->
                                NotificationsFragment.mContext.markFinished(notice.itemId!!,
                                    it1
                                )
                            }
                        }
                    }.show()
            }
        }
    }

    override fun getItemCount(): Int {
        if(notifications==null)return 0
        return notifications!!.size
    }
}