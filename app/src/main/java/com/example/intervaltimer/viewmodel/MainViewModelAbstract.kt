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

interface MainViewModelAbstract{
    val timerListFlow: Flow<List<TimerEntity>>

    fun addTimer (timer: TimerEntity)
    fun removeTimer (timer: TimerEntity)
}

@HiltViewModel
class MainViewModel @Inject constructor(private val timerRepository: TimerRepository): ViewModel(), MainViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val timerListFlow: Flow<List<TimerEntity>> = timerRepository.getAllFlow()

    override fun addTimer (timer: TimerEntity) {
        ioScope. launch {
            timerRepository.insert(timer)
        }
    }
    override fun removeTimer (timer: TimerEntity) {
        ioScope.launch {
            timerRepository.delete(timer)
        }
    }
}