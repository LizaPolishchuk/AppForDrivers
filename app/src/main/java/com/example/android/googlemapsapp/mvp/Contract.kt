package com.example.android.googlemapsapp.mvp

import com.example.android.googlemapsapp.data.AllData

interface Contract {
    interface Model {
        interface OnGettingData {
            fun onFinished(data: MutableList<AllData.MyData>)
            fun onFailure(t: Throwable)
        }

        fun getData(onGettingData: OnGettingData)
    }

    interface View {
        fun showData(data: MutableList<AllData.MyData>)
    }

    interface Presenter {
        fun getDataFromModel()
        fun onDestroy()
    }
}