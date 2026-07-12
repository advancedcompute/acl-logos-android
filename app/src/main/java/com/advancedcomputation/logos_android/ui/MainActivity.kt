package com.advancedcomputation.logos_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity()
{


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topToolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.topToolbar,
            R.string.app_name,
            R.string.app_name
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_profile ->
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()

                R.id.menu_security ->
                    Toast.makeText(this, "Security", Toast.LENGTH_SHORT).show()

                R.id.menu_transactions ->
                    Toast.makeText(this, "Transactions", Toast.LENGTH_SHORT).show()

                R.id.menu_settings ->
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

                R.id.menu_about ->
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            }

            binding.drawerLayout.closeDrawers()

            true
        }

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_chats ->
                    Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show()

                R.id.nav_wallet ->
                    Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show()

                R.id.nav_contacts ->
                    Toast.makeText(this, "Contacts", Toast.LENGTH_SHORT).show()

                R.id.nav_settings ->
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    /**
     * A native method that is implemented by the 'logos_android' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String


}