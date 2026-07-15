package com.advancedcomputation.logos_android.data

import com.advancedcomputation.logos_android.db.Wallet
import com.advancedcomputation.logos_android.db.WalletDao
import java.util.UUID

class WalletRepository(private val walletDao: WalletDao)
{
    var wallets = walletDao.getWallets()

    suspend fun createWallet(walletName: String, walletCurrency: String = "ACL")
    {
        walletDao.insert(
            Wallet(
                walletId = UUID.randomUUID().toString(),
                name = walletName,
                currency = walletCurrency,
                balance = 0.0)
        )
    }

    suspend fun getWallet(walletId: Int): Wallet?
    {

        try {
            return walletDao.getWallet(walletId)
        } catch(ex: Exception) {
            // TODO
        }
        return null
    }

    suspend fun updateWallet(wallet: Wallet, newName: String)
    {
        val updated = wallet.copy(name = newName)
        walletDao.update(updated)
    }

    suspend fun deleteWallet(wallet: Wallet)
    {
        walletDao.delete(wallet)
    }

    suspend fun loadWallets(): List<Wallet>
    {
        return walletDao.getWallets()
    }

}