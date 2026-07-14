package com.advancedcomputation.logos_android.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.advancedcomputation.logos_android.LogosApplication
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.crypto.AsymmetricKey
import com.advancedcomputation.logos_android.db.AppDatabase
import com.advancedcomputation.logos_android.db.Identity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID


class SplashActivity : AppCompatActivity()
{
    private fun onStartUp(app: LogosApplication)
    {
        val secureDir = File(applicationContext.filesDir, "secure")
        if (!secureDir.exists()) {
            secureDir.mkdirs()
        }
        app.filepathRootDir = secureDir.absolutePath
        val dbFile = File(app.filepathRootDir, app.dbName)
        //if(dbFile.exists()) {
        //    dbFile.delete()
        //}

        app.databaseFilepath = dbFile.absolutePath
        app.database = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, dbFile.absolutePath
        ).build()

        //app.database.clearAllTables()
        app.deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        var existingIdentity: Identity? = app.database.identityDao().getIdentity()
        if(existingIdentity == null)
        {
            app.deviceKey = AsymmetricKey()
            app.deviceKey.CreateKey()
            app.serviceId = UUID.randomUUID().toString()
            existingIdentity = Identity(
                deviceId = app.deviceId,
                serviceId = app.serviceId,
                publicKey = app.deviceKey.GetPublicKeyPEM(),
                privateKey = app.deviceKey.GetPrivateKeyPEM(),
                created = System.currentTimeMillis() / 1000)
            app.database.identityDao().insert(existingIdentity);

            Log.i("APP", "Using (deviceId: ${app.deviceId}; serviceId: ${app.serviceId})")
        } else {
            app.serviceId = existingIdentity.serviceId
            app.deviceKey = AsymmetricKey()
            app.deviceKey.CreateKey()       // Temporary easy solution (ignore the fact we are immediately overwriting the key with different values)
            app.deviceKey.LoadKey(existingIdentity.publicKey, existingIdentity.privateKey)

            Log.i("APP", "Using (deviceId: ${app.deviceId}; serviceId: ${app.serviceId})")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("cpputils_android")
        lifecycleScope.launch(Dispatchers.IO)
        {
            val app = application as LogosApplication
            onStartUp(app)
        }

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this, WalletActivity::class.java)
            )
            finish()
        }, 3000)
    }
}