package com.example.android.googlemapsapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.android.googlemapsapp.App
import com.example.android.googlemapsapp.data.AllData

@Database(entities = [AllData.MyData::class], version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun getDao(): DaoData

    companion object {
        private var database: DatabaseHelper? = null
        fun getDatabase(): DatabaseHelper? {
            if (database == null) {
                synchronized(DatabaseHelper::class.java) {
                    database = Room.databaseBuilder(App.instance, DatabaseHelper::class.java, "geoDatabase")
                            .build()
                }
            }
            return database
        }
    }
}