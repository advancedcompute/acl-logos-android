package com.advancedcomputation.logos_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbVersion(
    @PrimaryKey
    val id: Int = 1,
    val major: Int = 0,
    val minor: Int = 0,
    val patch: Int = 0,
    val revision: Int = 1,
    val created: Long
)