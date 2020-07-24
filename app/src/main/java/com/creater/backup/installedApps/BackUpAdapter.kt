package com.creater.backup.installedApps

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.creater.backup.R
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.creater.backup.databinding.OnerowdataBinding
import com.creater.backup.toastmessage

class BackUpAdapter(var onClickListener: OnClickListener) :
    RecyclerView.Adapter<BackUpAdapter.AppHolder>() {
    var appList = emptyList<AppData>()
        set(value) {
            field =value
            notifyDataSetChanged()
        }

    class AppHolder(var binding: OnerowdataBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onBindViewHolder(holder: AppHolder, position: Int) {
          var appData=appList[position]
            var rowIndex=-1
        holder.binding.also {
            it.property=appData
        }
            holder.binding.container.setOnClickListener {
                onClickListener.onClick(appData)
                rowIndex=position
                appData.selected = !appData.selected
                notifyItemChanged(position)
                Log.i("name",rowIndex.toString())
            }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AppHolder {
         return AppHolder(DataBindingUtil.inflate<OnerowdataBinding>(LayoutInflater.from(parent.context),R.layout.onerowdata,parent,false))
    }

    override fun getItemCount():Int {
        return appList.size
    }

}
class OnClickListener(var onClickListener: (appData:AppData)-> Unit){
    fun onClick(appData: AppData) =onClickListener(appData)
}
