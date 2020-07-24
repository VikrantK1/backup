package com.creater.backup

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.creater.backup.services.service
import com.creater.backup.services.serviceutlis.servicechange

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        servicechange(service::class.java,this)
    }
}
