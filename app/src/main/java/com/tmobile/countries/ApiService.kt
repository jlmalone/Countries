package com.tmobile.countries

import com.tmobile.countries.model.Country
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    //https://restcountries.eu/rest/v2/all
    @GET("v2/all")
   fun  getCountries(): Observable<List<Country>>
}