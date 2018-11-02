package com.example.android.googlemapsapp.database

import com.example.android.googlemapsapp.data.AllData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class DataRepository(private val daoData: DaoData){

     fun putData(data: MutableList<AllData.MyData>): Job{
        return launch(CommonPool){
            daoData.putData(data)
        }
    }

    fun deleteData(data: MutableList<AllData.MyData>): Job{
        return launch(CommonPool){
            daoData.deleteAllData(data)
        }
    }

    suspend fun getData(): MutableList<AllData.MyData>{
        return async(CommonPool){
            daoData.getData()
        }.await()
    }

}