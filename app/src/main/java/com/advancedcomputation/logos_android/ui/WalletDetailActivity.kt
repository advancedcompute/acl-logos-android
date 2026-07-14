package com.advancedcomputation.logos_android.ui

import android.os.Bundle
import com.advancedcomputation.logos_android.databinding.ActivityWalletDetailBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.advancedcomputation.logos_android.LogosApplication
import com.advancedcomputation.logos_android.data.WalletRepository
import com.advancedcomputation.logos_android.db.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletDetailActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityWalletDetailBinding

    private lateinit var repository: WalletRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as LogosApplication
        val walletId = intent.getIntExtra("Id", 1)

        lifecycleScope.launch(Dispatchers.IO) {
            repository = WalletRepository(app.database.walletDao())
            val wallet: Wallet? = repository.getWallet(walletId)
            if(wallet != null)
                binding.walletName.text = "Wallet: ${wallet.name}"
        }

        binding.walletToolbar.setOnClickListener {
            finish()
        }
    }

}