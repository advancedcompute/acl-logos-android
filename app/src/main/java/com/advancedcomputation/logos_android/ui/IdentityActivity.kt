package com.advancedcomputation.logos_android.ui;

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.databinding.ActivityIdentityBinding

class IdentityActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityIdentityBinding

    private fun setupDrawer() {
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
            when(it.itemId) {
                R.id.menu_profile -> {
                    val intent = Intent(this, IdentityActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_security -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                R.id.menu_transactions -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                R.id.menu_settings -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                R.id.menu_about -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawer()
    }
}
