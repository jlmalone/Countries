package com.tmobile.countries

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.tmobile.countries.model.Country
import com.tmobile.countries.model.STATE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object CountriesRepository {

    private var countriesLiveData = MutableLiveData<List<Country>>()
    private var currentState = MutableLiveData<STATE>().apply { this.postValue(STATE.NOT_STARTED) }

    @SuppressLint("CheckResult")
    fun refreshCountries(){
        currentState.postValue(STATE.LOADING)
        ApiManager.service.getCountries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

             .subscribe({
                 if(it!=null && it.isNotEmpty()) {
                     countriesLiveData.postValue(it)
                     currentState.postValue(STATE.LOADED)
                 }
             },{
                currentState.postValue(STATE.ERROR)
            })
    }

    fun getState():MutableLiveData<STATE>{
        return currentState
    }

    fun getCountries():MutableLiveData<List<Country>>{
        return countriesLiveData
    }
}