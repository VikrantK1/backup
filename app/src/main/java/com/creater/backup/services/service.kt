package com.creater.backup.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.creater.backup.reciever.reciever

class service:Service() {
    var recive=reciever()
    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)

        unregisterReceiver(recive)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext,"Service Start", Toast.LENGTH_SHORT).show()
        var intent=IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_INSTALL)
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addDataScheme("package")
        }
        registerReceiver(recive,intent)
        Log.i("name","start Service")
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("name","destory Service")
    }

}