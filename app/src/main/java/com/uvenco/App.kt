package com.uvenco

import android.app.Application
import androidx.room.Room
import com.uvenco.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(){
    private lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = Room
            .inMemoryDatabaseBuilder(
                applicationContext,
                AppDatabase::class.java,
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}