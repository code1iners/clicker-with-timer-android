package com.codeliner.clickerwithtimer.domains.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "first_name") val firstName: Int = 0,
    @ColumnInfo(name = "last_name") val lastName: Int = 0,
)