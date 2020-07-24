package com.creater.backup.installedApps

import android.graphics.drawable.Drawable
import android.media.DrmInitData
import android.widget.ImageView

data class AppData(var name:String,
                   var icons:Drawable,
                   var path:String,
                   var size:Double,
                   var exist:Boolean,
                   var selected:Boolean=false,
                   var installdate:Long
)
{
    fun size_value():String = String.format("%.2f",size)+"mb"
}

data class Progess_Data(var initData:Int=0,var finalData:Int=0)
