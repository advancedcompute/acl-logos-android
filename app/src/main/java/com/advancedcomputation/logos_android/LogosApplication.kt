package com.advancedcomputation.logos_android

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.advancedcomputation.logos_android.crypto.AsymmetricKey
import com.advancedcomputation.logos_android.db.AppDatabase

class LogosApplication: Application(), DefaultLifecycleObserver
{
    lateinit var database: AppDatabase
    lateinit var deviceKey: AsymmetricKey
    //lateinit var serviceKeys: Map<String, AsymmetricKey>
    //lateinit var groupKeys: Map<String, AsymmetricKey>
    //lateinit var contactListKeys: Map<String, AsymmetricKey>

    val dbName: String = "logos.db"
    var deviceId: String = ""
    var serviceId: String = ""
    var filepathRootDir: String = ""
    var databaseFilepath: String = ""


    override fun onCreate()
    {
        Application().onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("APP", "App entered foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("APP", "App entered background")
    }
}