package com.advancedcomputation.logos_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.advancedcomputation.logos_android.LogosApplication
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.databinding.ActivityMainBinding
import com.advancedcomputation.logos_android.db.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var wallets: List<Wallet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as LogosApplication
        lifecycleScope.launch(Dispatchers.IO)
        {
            wallets = app.database.walletDao().getWallets()
            if(wallets.isEmpty())
            {
                var wallet = Wallet(
                    id = 1,
                    walletId = UUID.randomUUID().toString(),
                    name = "Testing wallet",
                    currency = "ACL",
                    balance = 0.0,
                    fiatValue = 0.0)
                app.database.walletDao().insert(wallet)
            }
        }

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

        loadWallets()

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

            when(it.itemId) {
                R.id.nav_wallet ->
                    Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }


    private fun loadWallets() {

        val app = application as LogosApplication

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                //displayWallets(wallets)
            }

            runOnUiThread {
                binding.walletContainer.removeAllViews()
                wallets.forEach { wallet ->

                    val row = layoutInflater.inflate(
                        R.layout.wallet_item,
                        binding.walletContainer,
                        false
                    )

                    row.findViewById<TextView>(R.id.walletName).text = wallet.name
                    row.findViewById<TextView>(R.id.walletBalance).text = "${wallet.balance} ${wallet.currency}"
                    binding.walletContainer.addView(row)
                }
            }
        }
    }
}