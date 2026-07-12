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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("native-lib")        // cpputils pulled in via native-lib
        lifecycleScope.launch(Dispatchers.IO)
        {
            val app = application as LogosApplication

            val secureDir = File(applicationContext.filesDir, "secure")
            if (!secureDir.exists()) {
                secureDir.mkdirs()
            }
            app.filepathRootDir = secureDir.absolutePath

            val dbFile = File(app.filepathRootDir, app.dbName)
            app.databaseFilepath = dbFile.absolutePath
            app.database = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, dbFile.absolutePath
            ).build()


            var existingIdentity: Identity? = app.database.identityDao().getIdentity()
            if(existingIdentity == null)
            {
                val deviceKey: AsymmetricKey = AsymmetricKey()
                deviceKey.CreateKey()

                existingIdentity = Identity(
                    deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
                    serviceId = UUID.randomUUID().toString(),
                    publicKey = deviceKey.GetPublicKeyPEM(),
                    privateKey = deviceKey.GetPrivateKeyPEM(),
                    created = System.currentTimeMillis() / 1000)
                app.database.identityDao().insert(existingIdentity);
            }

            Log.i("APP", "Using (deviceId: ${app.deviceId}; serviceId: ${app.serviceId})")
        }

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, 5000)
    }
}