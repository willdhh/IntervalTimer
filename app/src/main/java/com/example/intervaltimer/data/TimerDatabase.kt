package com.example.intervaltimer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimerEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao

    companion object {
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "table.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}