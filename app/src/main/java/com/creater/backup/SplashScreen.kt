package com.creater.backup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SplashScreen : AppCompatActivity() {
    var listOfPermission= listOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var handler=Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)
        if (allpermissionGranted())
        {
         main_activity_Page()
        }
        else
        {
            ActivityCompat.requestPermissions(this,listOfPermission.toTypedArray(),123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==123)
        {
            if (allpermissionGranted())
            {
                main_activity_Page()
            }
            else
            {
                toastmessage(applicationContext,"All Permission Must be Granted")
                finish()
            }
        }
    }
    fun main_activity_Page()
    {
        handler.postDelayed(Runnable { startActivity(Intent(this@SplashScreen,MainActivity::class.java)) },2000)

    }

    private fun allpermissionGranted()=listOfPermission.all {
        ContextCompat.checkSelfPermission(baseContext,it)== PackageManager.PERMISSION_GRANTED
    }

}