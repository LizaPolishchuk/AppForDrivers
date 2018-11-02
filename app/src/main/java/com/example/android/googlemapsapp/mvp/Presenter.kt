package com.example.android.googlemapsapp.mvp

import android.util.Log
import com.example.android.googlemapsapp.data.AllData
import com.example.android.googlemapsapp.database.DataRepository
import com.example.android.googlemapsapp.database.DatabaseHelper

class Presenter(var view: Contract.View?) : Contract.Presenter, Contract.Model.OnGettingData{
    override fun onFailure(t: Throwable) {
        Log.d("myLogs", "$t")
    }

    override fun onFinished(data: MutableList<AllData.MyData>) {
        view?.showData(data)
    }

    private val model: Contract.Model
    init {
        model = Model(DataRepository(DatabaseHelper.getDatabase()!!.getDao()))
    }
    override fun getDataFromModel() {
        model.getData(this)
    }

    override fun onDestroy() {
        view = null
    }

}