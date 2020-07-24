package com.creater.backup.reciever

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.net.toFile
import com.creater.backup.FileManager.FileManagerOfBackup
import com.creater.backup.toastmessage
import java.io.File
import java.net.URL

class reciever:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

      //       toastmessage(context!!,"Package is Installed")
               var action=intent?.action
                  if(Intent.ACTION_PACKAGE_ADDED.equals(action))
                  {
                      var pack23=context.packageManager.getInstalledPackages(0)

                      for (d23 in pack23)
                      {
                          Log.i("name",d23.packageName)
                          var data1=d23.packageName
                          var data2=intent?.data?.encodedSchemeSpecificPart
                          if (data1==data2)
                          {
                              Log.i("name",d23.applicationInfo.sourceDir)
                            var dist=FileManagerOfBackup.folder.getBackupDirectory(context).toString()+"/"+d23.applicationInfo.loadLabel(context.packageManager).toString()+".apk"
                             if(!File(dist).exists())
                             {
                                 File(d23.applicationInfo.sourceDir).copyTo(File(dist))
                             }
                              else
                             {
                                 File(dist).deleteOnExit()
                                 File(d23.applicationInfo.sourceDir).copyTo(File(dist))

                             }
                          }
                      }
                  }

//             var packageManager =intent?.data
//             Log.i("name",packageManager?.path)

    }
}