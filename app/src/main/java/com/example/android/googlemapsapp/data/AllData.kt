package com.example.android.googlemapsapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

class AllData {
    @Entity
    data class MyData(@PrimaryKey(autoGenerate = true) val key: Int,
                      val name: String?,
                      val lat: Double,
                      val lng: Double,
                      val categoryId: Int)

    data class Data(val data: MutableList<MyData>)
}
