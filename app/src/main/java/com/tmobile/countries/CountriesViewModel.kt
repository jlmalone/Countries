package com.tmobile.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmobile.countries.model.STATE

class CountriesViewModel : ViewModel(){

    var stateLiveData = CountriesRepository.getState().also { it.postValue(STATE.NOT_STARTED) }
    var stateFiltered = MutableLiveData<STATE>()
    var countriesLiveData = CountriesRepository.getCountries()

    fun fetchData()
    {
        CountriesRepository.refreshCountries()
    }
}