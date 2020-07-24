package com.creater.backup.installedApps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AppsToBackUpViewModelFactory(val context: Context) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppsToBackUpViewModel::class.java))
        {
            return AppsToBackUpViewModel(context) as T
        }
        throw IllegalArgumentException("Something is wrong here")
    }
}