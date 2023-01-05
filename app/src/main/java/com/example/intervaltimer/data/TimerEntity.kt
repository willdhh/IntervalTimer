package com.example.intervaltimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "timer")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null,

    @ColumnInfo(name = "timer_title")
    val timerTitle: String,

    @ColumnInfo(name = "warmup")
    val warmup: Long?,

    @ColumnInfo(name = "high_intensity")
    val highIntensity: Long?,

    @ColumnInfo(name = "low_intensity")
    val lowIntensity: Long?,

    @ColumnInfo(name = "cooldown")
    val coolDown: Long?

)