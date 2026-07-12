package com.advancedcomputation.logos_android.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[Identity::class, Wallet::class], version=1)
abstract class AppDatabase: RoomDatabase()
{
    abstract fun identityDao(): IdentityDao

    abstract fun walletDao(): WalletDao
}