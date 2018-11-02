package com.example.android.googlemapsapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.android.googlemapsapp.data.AllData

@Dao
interface DaoData {
    @Insert
    fun putData(data: MutableList<AllData.MyData>)
    @Delete
    fun deleteAllData(data: MutableList<AllData.MyData>)
    @Query("SELECT*FROM mydata")
    fun getData(): MutableList<AllData.MyData>
}