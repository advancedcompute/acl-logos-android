package com.advancedcomputation.logos_android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advancedcomputation.logos_android.data.WalletRepository
import com.advancedcomputation.logos_android.db.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WalletViewModel(private val repository: WalletRepository) : ViewModel()
{
    private val _wallets = MutableStateFlow<List<Wallet>>(emptyList())
    val wallets: StateFlow<List<Wallet>> = _wallets

    fun loadWallets() {
        viewModelScope.launch {
            val wallets = withContext(Dispatchers.IO) {
                    var result = repository.loadWallets()
                    if(result.isEmpty()) {
                        repository.createWallet("My Wallet")
                        result = repository.loadWallets()
                    }
                    result
                }
            _wallets.value = wallets
        }
    }

    fun createWallet(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.createWallet(name)
            }
            loadWallets()
        }
    }

    fun updateWallet(wallet: Wallet, name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateWallet(
                    wallet,
                    name
                )
            }
            loadWallets()
        }
    }

    fun deleteWallet(wallet: Wallet) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteWallet(wallet)
            }
            loadWallets()
        }
    }
}