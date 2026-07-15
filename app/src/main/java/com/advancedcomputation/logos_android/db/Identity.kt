package com.advancedcomputation.logos_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Identity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deviceId: String,
    val serviceId: String,
    val publicKey:String,
    val privateKey: String,
    val verified: Boolean,
    val created: Long,
    val name: String,
    val email: String,
    //val metadata: String
)