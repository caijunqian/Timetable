package com.example.timetable.ui.notifications

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.R
import com.example.timetable.httpReq.ItemBean
import kotlinx.android.synthetic.main.cell.view.*
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(){
//    var allEntrys :List<Entry>?=null
//    var dao:EntryDao ?=null
    var notifications:List<ItemBean.Data>?=null
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var textViewPos:TextView?=itemView.findViewById(R.id.textViewPos)
        var textViewEntry:TextView?=itemView.findViewById(R.id.textViewEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater= LayoutInflater.from(parent.context);
        val itemView:View=layoutInflater.inflate(R.layout.cell,parent,false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notice = notifications?.get(position)
        holder.textViewPos!!.text=(position+1).toString()
        print("===============2222===")
        if (notice != null) {
            holder.textViewEntry!!.text= "${notice.endTime}   name:  ${notice.name}"
            print("==================")
        }
        /*
        var entry:Entry = allEntrys!![position]
        var id: Int? = allEntrys!![position]!!.id
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        var strDate = sdf.format(entry.date)
        //把id放进去
        //holder.textViewPos!!.contentDescription=id.toString()
        holder.textViewPos!!.text=(position+1).toString()
        holder.textViewEntry!!.text= "$strDate   calories:  ${entry.amount}"
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).setTitle("提示").setMessage("您确定删除该记录吗？")
                .setPositiveButton("确定", DialogInterface.OnClickListener { _, _ ->
                    Log.d("myTag", "onBindViewHolder:确定")
                    if (id != null) {
                        dao?.delete(Entry(id,null,null))
                    }
                    //删除完进行更新视图
                    this.allEntrys=dao!!.loadAll().sortedBy { it.date }
                    this.notifyDataSetChanged()
                })
                .setNegativeButton("取消", null).show()
        }
         */
    }

    override fun getItemCount(): Int {
//        return allEntrys!!.size
        if(notifications==null)return 0
        return notifications!!.size
    }
}