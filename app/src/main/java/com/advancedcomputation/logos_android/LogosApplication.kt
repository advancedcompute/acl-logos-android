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
    public lateinit var database: AppDatabase
    public lateinit var deviceKey: AsymmetricKey
    //lateinit var serviceKeys: Map<String, AsymmetricKey>
    //lateinit var groupKeys: Map<String, AsymmetricKey>
    //lateinit var contactListKeys: Map<String, AsymmetricKey>

    public val dbName: String = "logos.db"
    public var deviceId: String = ""
    public var serviceId: String = ""
    public var filepathRootDir: String = ""
    public var databaseFilepath: String = ""


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