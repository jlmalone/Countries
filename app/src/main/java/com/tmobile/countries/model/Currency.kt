package com.tmobile.countries.model

import com.squareup.moshi.Json

class Currency {
    @Json(name = "code")
    var code: String? = null

    @Json(name = "name")
    var name: String? = null

    @Json(name = "symbol")
    var symbol: String? = null
}