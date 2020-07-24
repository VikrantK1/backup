package com.creater.backup.installedApps

import android.app.AlertDialog

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageItemInfo
import android.content.pm.PackageManager
import android.media.audiofx.AudioEffect
import android.os.Environment
import android.os.FileUtils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creater.backup.FileManager.FileManagerOfBackup
import com.creater.backup.FileManager.FileManagerOfBackup.folder.getBackupDirectory
import com.creater.backup.R


import com.creater.backup.toastmessage
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.nio.Buffer
import java.sql.Array

class AppsToBackUpViewModel(val context: Context) : ViewModel() {
  private  val _listOfApps=MutableLiveData<List<AppData>>()
    private val _noOfSelectedApps=MutableLiveData<Int>()
    val noOfselectedApps:LiveData<Int> =_noOfSelectedApps
    val dilouge=AlertDialog.Builder(context)
    val  listOfApps:LiveData<List<AppData>> =_listOfApps
    val _progressbarVisible=MutableLiveData<Boolean>(true)
    var list_ofSelected_Apps= mutableListOf<AppData>()
    var viewModelJob= Job()
    var uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)
   init {

       get_AllInstalledApp()
      _noOfSelectedApps.value=0
   }


    fun get_AllInstalledApp(){
        var packs=context.packageManager.getInstalledPackages(0)
        var packageManager=context.packageManager
        if (packs.isNotEmpty())
        {
            uiScope.launch {
                _listOfApps.value=allApps(packs,packageManager)
                sortData()
                _progressbarVisible.value=false
            }
        }
    }

  private suspend   fun allApps(packs:MutableList<PackageInfo>,packageManager: PackageManager):List<AppData>
    {
     return   withContext(Dispatchers.IO)
        {
            var list_Of_Apps= mutableListOf<AppData>()
            for (app in packs) {
                var name = app.applicationInfo.loadLabel(packageManager).toString()
                var icon = app.applicationInfo.loadIcon(packageManager)
                var path = app.applicationInfo.sourceDir
                var filelength=(File(path).length().toDouble()/(1024*1024))
                var installDate=File(path).lastModified()
                var exist=false
                if(File(getBackupDirectory(context).toString()+"/"+name+".apk").exists())
                {
                    exist=true

                }
                if (filelength>3 && installDate!=0L)
                {
                    list_Of_Apps.add(AppData(name,icon,path,filelength,exist,false,installDate))
                //    _listOfApps.postValue(list_Of_Apps)

                }
            }
            list_Of_Apps
        }

    }

    private suspend fun fileCopy(list:List<AppData>)
    {
        withContext(Dispatchers.IO){
           for (app in list)
           {
              try {
                  if(!File(getBackupDirectory(context).toString()+"/"+app.name+".apk").exists())
                  {
                      File(app.path).copyTo(File(getBackupDirectory(context).toString()+"/"+app.name+".apk"))

                  }
                  else
                  {
                      File(getBackupDirectory(context).toString()+"/"+app.name+".apk").delete()
                      File(app.path).copyTo(File(getBackupDirectory(context).toString()+"/"+app.name+".apk"))
                  }
              } catch (e:Exception)
              {

              }
           }
        }
    }
    fun sortData() {
      var list:MutableList<AppData> = _listOfApps.value as MutableList<AppData>
        var sharepref=context.getSharedPreferences("service",Context.MODE_PRIVATE)

        if (sharepref.getBoolean("installed",true))
        {
            list.sortByDescending { it.installdate }
        }
        if (sharepref.getBoolean("size",false))
        {
            list.sortByDescending { it.size }
        }
        if (sharepref.getBoolean("name",false))
        {
            list.sortBy { it.name }
        }
        for (i:AppData in list)
        {
            Log.i("name",i.installdate.toString())
        }
        _listOfApps.value=list

    }
    fun BackupButtonClicked()
    {
       uiScope.launch {
           var layout:LayoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
          var  view=layout.inflate(R.layout.progessdiloge,null,false)
           dilouge.setView(view)
           var di=dilouge.create()
           di.show()
           fileCopy(list_ofSelected_Apps)
           list_ofSelected_Apps.removeAll(list_ofSelected_Apps)
           get_AllInstalledApp()
           _noOfSelectedApps.value=list_ofSelected_Apps.size
           di.cancel()
           Toast.makeText(context, getBackupDirectory(context).path.toString(),Toast.LENGTH_SHORT).show()
       }
    }

    fun selected_Apps(appData: AppData)
    {
        if(!list_ofSelected_Apps.contains(appData)){
            list_ofSelected_Apps.add(appData)
        }
        else
        {
            list_ofSelected_Apps.remove(appData)
        }
        _noOfSelectedApps.value=list_ofSelected_Apps.size
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}