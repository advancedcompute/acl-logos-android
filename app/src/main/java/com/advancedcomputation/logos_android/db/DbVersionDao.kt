package com.advancedcomputation.logos_android.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbVersionDao {

    @Insert
    fun insert(dbVersionEntry: DbVersion)

    //@Query("SELECT * FROM DbVersion LIMIT 1")
    //fun getIdentity(): Identity?


}