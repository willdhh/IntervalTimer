package com.example.intervaltimer.di

import android.app.Application
import com.example.intervaltimer.data.AppDatabase
import com.example.intervaltimer.data.TimerDao
import com.example.intervaltimer.data.TimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideTimerRepository(timerDao: TimerDao) :TimerRepository{
        return TimerRepository(timerDao = timerDao)
    }

    @Singleton
    @Provides
    fun provideTimerDatabase(app: Application) : AppDatabase{
        return AppDatabase.getInstance(context = app)
    }

    @Singleton
    @Provides
    fun provideTimerDao(timerDatabase: AppDatabase): TimerDao {
        return timerDatabase.timerDao()
    }

}