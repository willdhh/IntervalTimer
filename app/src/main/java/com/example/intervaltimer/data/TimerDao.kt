package com.example.intervaltimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM timer")
    fun getAll(): Flow<List<TimerEntity>>

    @Insert
    suspend fun insert(vararg timer: TimerEntity)

    @Query("SELECT * FROM timer WHERE uid = :key")
    suspend fun getTimer(key:Long?): TimerEntity

    @Delete
    suspend fun delete(timer: TimerEntity)

    @Query("DELETE FROM timer")
    suspend fun deleteAll()
}