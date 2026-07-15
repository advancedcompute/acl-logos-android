package com.advancedcomputation.logos_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val walletId: String,
    val name: String,
    val currency: String,
    val balance: Double
)