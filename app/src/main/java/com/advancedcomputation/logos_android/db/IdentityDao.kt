package com.advancedcomputation.logos_android.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IdentityDao {

    @Insert
    fun insert(identity:Identity)


    @Query("SELECT * FROM Identity LIMIT 1")
    fun getIdentity():Identity?


}