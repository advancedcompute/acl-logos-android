package com.advancedcomputation.logos_android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.advancedcomputation.logos_android.LogosApplication
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.data.WalletRepository
import com.advancedcomputation.logos_android.databinding.ActivityWalletBinding
import com.advancedcomputation.logos_android.db.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class WalletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletBinding

    private lateinit var adapter: WalletAdapter

    private lateinit var repository: WalletRepository

    private lateinit var viewModel: WalletViewModel

    private val MAX_WALLET_NAME_LEN: Int = 30

    private fun showCreateWalletDialog()
    {
        val view = layoutInflater.inflate(R.layout.dialog_create_wallet, null)
        val input = view.findViewById<EditText>(R.id.walletNameInput)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Create Wallet")
            .setView(view)
            .setPositiveButton("Create", null)   // We'll override this
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val createButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            createButton.setOnClickListener {
                val name = input.text.toString().trim()
                when {
                    name.isBlank() -> {
                        input.error = "Please enter a wallet name."
                    }

                    name.length > MAX_WALLET_NAME_LEN -> {
                        input.error = "Wallet name must be $MAX_WALLET_NAME_LEN characters or fewer."
                    }

                    else -> {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                repository.createWallet(name)
                            }

                            val wallets = withContext(Dispatchers.IO) {
                                repository.loadWallets()
                            }
                            adapter.updateWallets(wallets)
                            dialog.dismiss()
                        }
                    }
                }
            }
        }

        dialog.show()
    }


    private fun showWalletOptionsDialog(wallet: Wallet)
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_wallet, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.walletNameInput)
        nameInput.setText(wallet.name)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Wallet")
            .setView(dialogView)
            .setPositiveButton("Save", null)   // We'll override this
            .setNeutralButton("Delete", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val createButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            var deleteButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            createButton.setOnClickListener {
                val name = nameInput.text.toString().trim()
                when {
                    name.isBlank() -> {
                        nameInput.error = "Please enter a wallet name."
                    }

                    name.length > MAX_WALLET_NAME_LEN -> {
                        nameInput.error = "Wallet name must be $MAX_WALLET_NAME_LEN characters or fewer."
                    }

                    else -> {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                repository.updateWallet(wallet, name)
                            }

                            val wallets = withContext(Dispatchers.IO) {
                                repository.loadWallets()
                            }
                            adapter.updateWallets(wallets)
                            dialog.dismiss()
                        }
                    }
                }
            }
            deleteButton.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.deleteWallet(wallet)
                    }

                    val wallets = withContext(Dispatchers.IO) {
                        repository.loadWallets()
                    }
                    adapter.updateWallets(wallets)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun openWallet(wallet: Wallet) {
        val intent = Intent(this, WalletDetailActivity::class.java)
        intent.putExtra("Id", wallet.id)
        startActivity(intent)
    }

    private fun observeWallets() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wallets.collect { wallets ->
                    adapter.updateWallets(wallets)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as LogosApplication
        binding.walletRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = WalletAdapter(emptyList(),
            { wallet -> openWallet(wallet) },
            { wallet -> showWalletOptionsDialog(wallet) })

        binding.walletRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.walletRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            repository = WalletRepository((application as LogosApplication).database.walletDao())
            viewModel = WalletViewModel(repository)
        }

        observeWallets()
        viewModel.loadWallets()
        binding.addWalletButton.setOnClickListener { showCreateWalletDialog() }

        setSupportActionBar(binding.topToolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.topToolbar, R.string.app_name, R.string.app_name)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                R.id.menu_security -> Toast.makeText(this, "Security", Toast.LENGTH_SHORT).show()
                R.id.menu_transactions -> Toast.makeText(this, "Transactions", Toast.LENGTH_SHORT).show()
                R.id.menu_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                R.id.menu_about -> Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_wallet -> Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }


}