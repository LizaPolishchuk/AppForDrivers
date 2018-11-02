package com.example.android.googlemapsapp.mvp

import com.example.android.googlemapsapp.data.AllData
import com.example.android.googlemapsapp.database.DataRepository
import com.example.android.googlemapsapp.retrofit.MyRetrofit
import com.example.android.googlemapsapp.utils.CheckingConnection
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(val dataRepository: DataRepository) : Contract.Model {

    var data: MutableList<AllData.MyData> = mutableListOf()

    override fun getData(onGettingData: Contract.Model.OnGettingData) {
        /**If there is Internet connection getting data from the Retrofit*/
        if (CheckingConnection.hasConnected()) {
            MyRetrofit.apiData.getData().enqueue(object : Callback<AllData.Data> {
                override fun onResponse(call: Call<AllData.Data>, response: Response<AllData.Data>) {
                    data = response.body()!!.data
                    launch(CommonPool) {
                        dataRepository.deleteData(dataRepository.getData())
                    }
                    dataRepository.putData(data)
                    onGettingData.onFinished(data)
                }

                override fun onFailure(call: Call<AllData.Data>, t: Throwable) {
                    onGettingData.onFailure(t)
                }
            })
        } else {
            runBlocking {
                launch(CommonPool) {
                    data = dataRepository.getData()
                }.join()
            }
            onGettingData.onFinished(data)
        }
    }
}