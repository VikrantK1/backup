package com.creater.backup.services

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import com.creater.backup.R

object serviceutlis{
    fun servicechange(classname:Class<out Service>,context: Context)
    {
        var serviceShare= context.getSharedPreferences("service", Context.MODE_PRIVATE)
        var data=serviceShare.getBoolean("service",true)
        if(data && !isServiceRunning(classname,context))
        {
            context.startService(Intent(context, service::class.java))
            Log.i("name","service is started")
        }
        else
        {
            context.stopService(Intent(context, service::class.java))
            Log.i("name","service is stoped")
        }
    }
    fun StartOrStopService(context: Context)
    {
        var serviceShare= context.getSharedPreferences("service", Context.MODE_PRIVATE)
        var editor=serviceShare.edit()
        var data=serviceShare.getBoolean("service",true)
        editor.putBoolean("service",!data)
        servicechange(service::class.java,context)
    }
    fun isServiceRunning(servicename: Class<out Service>,context: Context):Boolean{
        val servicem =context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return servicem.getRunningServices(Int.MAX_VALUE).any {
            it.service.className==servicename.name
        }
    }
}
