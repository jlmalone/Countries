package com.tmobile.countries.model

import com.squareup.moshi.Json

class Country{
    @Json(name = "alpha2Code")
    var alpha2Code: String? = null
    @Json(name = "name")
    var name: String? = null
    @Json(name = "flag")
    var flag: String? = null
    @Json(name = "callingCodes")
    var callingCodes: List<String>? = null
    @Json(name = "capital")
    var capital:String? = null
    @Json(name = "region")
    var region: String? = null
    @Json(name = "subregion")
    var subregion:String? = null
    @Json(name = "timezones")
    var timezones: List<String>? = null
    @Json(name = "currencies")
    var currencies: List<Currency>? = null
    @Json(name = "languages")
    var languages: List<Language>? = null
}