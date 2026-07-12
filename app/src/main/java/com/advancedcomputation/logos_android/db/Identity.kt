package com.advancedcomputation.logos_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Identity(
    @PrimaryKey
    val id: Int = 1,
    val deviceId: String,
    val serviceId: String,
    val publicKey:String,
    val privateKey: String,
    val created: Long,
    //val metadata: String
)