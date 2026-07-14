package com.advancedcomputation.logos_android.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WalletDao
{
    @Query("SELECT * FROM Wallet")
    fun getWallets(): List<Wallet>

    @Query("SELECT * FROM Wallet WHERE Id = :id")
    suspend fun getWallet(id: Int): Wallet

    @Insert
    fun insert(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Delete
    fun delete(wallet: Wallet)

}