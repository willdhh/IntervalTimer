package com.example.intervaltimer.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class TimerRepository(private val timerDao: TimerDao) {

    fun getAllFlow(): Flow<List<TimerEntity>> = timerDao.getAll()

    suspend fun insert(timer: TimerEntity) = timerDao.insert(timer)

    suspend fun delete(timer: TimerEntity) = timerDao.delete(timer)
}