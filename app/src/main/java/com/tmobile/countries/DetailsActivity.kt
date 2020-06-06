package com.tmobile.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tmobile.countries.databinding.DetailsActivityBinding

//One might just as easily use the Navigation Graph and One Activity App
//with 2 Fragments. I have used 2 activities as an arbitrary choice
//in part out of deference to the recommendations of the great Jake Wharton
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val COUNTRY_2_CODE_KEY = "COUNTRY_2_CODE_KEY"

        fun startIntent(context: Context, country2Code: String) =
            Intent(context, DetailsActivity::class.java).also {
                it.putExtra(COUNTRY_2_CODE_KEY, country2Code)
            }
    }

    private var detailsActivityBinding: DetailsActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val country2Code = intent.extras?.getString(COUNTRY_2_CODE_KEY)
        detailsActivityBinding = DataBindingUtil.setContentView<DetailsActivityBinding>(this, R.layout.details_activity)
        detailsActivityBinding?.detailsViewModel =
            ViewModelProvider(this).get(CountryDetailsViewModel::class.java)
        detailsActivityBinding?.detailsViewModel?.fetchCountryLiveData(country2Code)?.observe(this,
            Observer{ t ->
                actionBar?.title = t?.name
                supportActionBar?.title = t?.name
            })


    }
}