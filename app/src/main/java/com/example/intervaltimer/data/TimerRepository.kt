package com.example.intervaltimer.data

import kotlinx.coroutines.flow.Flow

class TimerRepository(private val timerDao: TimerDao) {

    fun getAllFlow(): Flow<List<TimerEntity>> = timerDao.getAll()

    suspend fun insert(timer: TimerEntity) = timerDao.insert(timer)

    suspend fun delete(timer: TimerEntity) = timerDao.delete(timer)

    suspend fun deleteAll() = timerDao.deleteAll()
}