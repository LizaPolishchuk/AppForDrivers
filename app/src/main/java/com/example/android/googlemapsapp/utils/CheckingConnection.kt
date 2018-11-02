package com.example.android.googlemapsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.android.googlemapsapp.App

class CheckingConnection {

    companion object {
    fun hasConnected(): Boolean {
        val manager: ConnectivityManager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if(networkInfo!=null && networkInfo.isConnected) return true
        return false
    }
}
}