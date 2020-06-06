package com.tmobile.countries

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiManager {

    var interceptor =  HttpLoggingInterceptor(). apply { level = HttpLoggingInterceptor.Level.BODY }

    var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    var service: ApiService = Retrofit.Builder()
        .baseUrl("https://restcountries.eu/rest/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}