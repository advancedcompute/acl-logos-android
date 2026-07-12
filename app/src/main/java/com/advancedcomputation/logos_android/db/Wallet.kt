package com.advancedcomputation.logos_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(
    @PrimaryKey
    val id: Int = 1,
    val walletId: String,
    val name: String,
    val currency: String,
    val balance: Double,
    val fiatValue: Double
)