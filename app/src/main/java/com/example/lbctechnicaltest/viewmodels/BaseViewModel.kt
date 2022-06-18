package com.example.lbctechnicaltest.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.lbctechnicaltest.models.utils.NetworkState
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    companion object {
        const val TAG = "BaseViewModel"
    }

    var loaderVisible = MutableLiveData<Boolean>()
    var networkState = MutableLiveData<NetworkState>()

    val disposableList = mutableListOf<Disposable>()

    override fun onCleared() {
        Log.v(TAG, "onCleared")

        super.onCleared()

        disposableList.forEach { if (!it.isDisposed) it.dispose() }
    }
}