package com.codeliner.clickerwithtimer.domains.scores

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScoreDatabaseDao {

    @Insert
    fun insert(score: Score)

    @Update
    fun update(score: Score)

    @Query("DELETE FROM score_table")
    fun clear()

    @Query("SELECT * FROM score_table ORDER BY id DESC")
    fun getAllScores(): LiveData<List<Score>>

    @Query("SELECT * FROM score_table WHERE id = :scoreId")
    fun getScoreById(scoreId: Long): Score

    @Query("SELECT * FROM score_table ORDER BY id DESC LIMIT 1")
    fun getScoreLatest(): Score
}