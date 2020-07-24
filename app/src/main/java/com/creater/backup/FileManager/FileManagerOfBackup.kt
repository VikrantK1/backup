package com.creater.backup.FileManager

import android.content.Context
import android.os.Environment
import android.util.Log
import com.creater.backup.R
import java.io.File

class FileManagerOfBackup {
  object folder{
      fun getBackupDirectory(context: Context): File {
          var file=File("/storage/emulated/0")
          var checkFolder=context.getSharedPreferences("service",Context.MODE_PRIVATE)

          if(checkFolder.getBoolean("checkFolder",true))
          {
              var backup=File(file,"Backup")
              var editdata=checkFolder.edit()
              if (!backup.exists())
              {
                  backup.mkdirs()
              }
              if (!backup.exists())
              {
                  editdata.putBoolean("checkFolder",false)
                  editdata.commit()
              }
          }
          if (!checkFolder.getBoolean("checkFolder",true))
          {
              context.externalMediaDirs.firstOrNull().let {
                  file=it!!
              }
          }
          Log.i("name",file.path.toString())
          var backdir=File(file,"backup")
          if (!backdir.exists())
          {
              backdir.mkdirs()
          }
          return backdir
      }
  }
}