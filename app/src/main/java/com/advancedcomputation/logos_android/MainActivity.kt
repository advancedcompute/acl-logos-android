package com.advancedcomputation.logos_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.advancedcomputation.logos_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'logos_android' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        init {
            //System.loadLibrary("cpputils")    // cpputils pulled in via native-lib
            System.loadLibrary("native-lib")
        }
    }
}