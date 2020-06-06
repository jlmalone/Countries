package com.tmobile.countries.model

import com.squareup.moshi.Json

class Language {

    @Json(name="name")
    var name:String?=null
    //there are other field we are ignoring for now
}

//"languages":[{"iso639_1":"ps","iso639_2":"pus","name":"Pashto","nativeName":"پښتو"},{"iso639_1":"uz","iso639_2":"uzb","name":"Uzbek","nativeName":"Oʻzbek"},