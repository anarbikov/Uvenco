package com.uvenco.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DrinkDB::class
    ],
    version = 1, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}