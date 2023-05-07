package com.example.intervaltimer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.intervaltimer.data.TimerEntity
import com.example.intervaltimer.data.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainViewModelAbstract {
    val timerList: List<TimerEntity>
    val timerListFlow: Flow<List<TimerEntity>>


    fun collectCurr()

    suspend fun getTimer(key: Long?): TimerEntity
    fun addTimer(timer: TimerEntity)
    fun removeTimer(timer: TimerEntity)
    fun deleteAll()
}

@HiltViewModel
class MainViewModel @Inject constructor(private val timerRepository: TimerRepository
) : ViewModel(),
    MainViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val timerListFlow: Flow<List<TimerEntity>> = timerRepository.getAllFlow()
    override var timerList: List<TimerEntity> = emptyList()

    init {
        collectCurr()
    }

    override fun collectCurr() {
        ioScope.launch {
            timerListFlow.collect() {
                timerList = it
            }
        }
    }

    override suspend fun getTimer(key: Long?): TimerEntity {

        return timerRepository.getTimer(key)
    }

    override fun addTimer(timer: TimerEntity) {
        ioScope.launch {
            timerRepository.insert(timer)
        }
    }

    override fun removeTimer(timer: TimerEntity) {
        ioScope.launch {
            timerRepository.delete(timer)
        }
    }

    override fun deleteAll() {
        ioScope.launch {
            timerRepository.deleteAll()
        }
    }
}