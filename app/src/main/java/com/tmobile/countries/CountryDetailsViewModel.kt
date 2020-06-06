package com.tmobile.countries

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadrosid.svgloader.SvgLoader
import com.tmobile.countries.model.Country

class CountryDetailsViewModel : ViewModel() {

    lateinit var countryLiveData: MutableLiveData<Country>
    lateinit var callingCodeLiveData: MutableLiveData<String>
    lateinit var regionLiveData: MutableLiveData<String>
    lateinit var languageLiveData: MutableLiveData<String>
    lateinit var subregionLiveData:MutableLiveData<String>
    lateinit var currencyLiveData:MutableLiveData<String>

    private var currentCountry2Code: String? = null

    companion object {
        //generally we do not like to make reference to views inside the viewmodel, but this
        //is a static helper method for facilitating data view binding
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView?, url: String?) {
            view?.run{
                if(view.context is Activity && url!=null)
                {
                    SvgLoader.pluck()
                        .with(view.context as Activity)
                        .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                        .load(url, this)
                }
            }
        }
    }

    fun fetchCountryLiveData(country2Code: String?): MutableLiveData<Country> {
        if (!this::countryLiveData.isInitialized || (country2Code != null && currentCountry2Code != country2Code)) {
            countryLiveData = MutableLiveData<Country>()
            callingCodeLiveData = MutableLiveData<String>()
            regionLiveData = MutableLiveData<String>()
            languageLiveData = MutableLiveData<String>()
            subregionLiveData = MutableLiveData<String>()
            currencyLiveData = MutableLiveData<String>()
            countryLiveData.observeForever {
                callingCodeLiveData.postValue(callingCodesString())
                regionLiveData.postValue(regionString())
                languageLiveData.postValue(languageString())
                subregionLiveData.postValue(subregionString())
                currencyLiveData.postValue(currencyString())
            }
            CountriesRepository.getCountries().observeForever {
                it.firstOrNull { country -> country.alpha2Code == country2Code }
                    ?.run {
                        countryLiveData.postValue(this)
                        callingCodeLiveData.postValue(callingCodesString())
                        regionLiveData.postValue(regionString())
                        languageLiveData.postValue(languageString())
                        subregionLiveData.postValue(subregionString())
                        currencyLiveData.postValue(currencyString())
                    }
            }
        }
        return countryLiveData
    }

    private fun callingCodesString(): String? {
        val callingCodes = countryLiveData.value?.callingCodes
        return return if (callingCodes?.isNotEmpty() == true) null
        else CountriesApplication.applicationContext()
            .getString(R.string.calling_codes_label, callingCodes?.joinToString (separator = ", "))
    }

    private fun subregionString(): String? {
        val sr = countryLiveData.value?.subregion
        return if (sr?.isNotEmpty() == true)
            CountriesApplication.applicationContext().getString(R.string.subregion_label, sr)
        else null
    }

    private fun regionString(): String? {
        val region = countryLiveData.value?.region
        return if (region?.isNotEmpty() == true)
            CountriesApplication.applicationContext().getString(R.string.region_label, region)
        else null
    }

    private fun languageString():String?{
        val language = countryLiveData.value?.languages?.mapNotNull { it.name }?.joinToString (separator = ", ")
        return if(language?.isNotEmpty() == true)
            CountriesApplication.applicationContext().getString(R.string.languages_label,language)
        else null
    }

    private fun currencyString():String?{
        val currency = countryLiveData.value?.currencies?.map { "${it.symbol} ${it.code} ${it.name}" }?.joinToString (separator = ", ")
        return if(currency?.isNotEmpty() == true)
            CountriesApplication.applicationContext().getString(R.string.currency_label,currency)
        else null
    }


}