package com.codeliner.clickerwithtimer.domains.scores

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class Score (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "score")
    var score: Int = -1,
    @ColumnInfo(name = "created")
    var created: Long = System.currentTimeMillis()
)