package com.tmobile.countries

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tmobile.countries.model.Country
import com.tmobile.countries.model.STATE
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private var adapter: CountryAdapter? = null
    lateinit var searchView: SearchView
    private lateinit var viewModel: CountriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)
        viewModel.stateLiveData.observe(this,
            Observer { state ->
                if (state == STATE.LOADED && viewModel.stateFiltered.value == null) {
                    adapter?.filter?.filter(searchView.query)
                    return@Observer
                }

                updateUi()
                if (state == STATE.NOT_STARTED) {
                    viewModel.fetchData()
                }
            })

        viewModel.countriesLiveData.observe(this, Observer {
            updateUi()
        })
        viewModel.stateFiltered.observe(this, Observer {
            updateUi()
        })
        setUpRecyclerView()
        retry_button.setOnClickListener { viewModel.fetchData() }
    }

    private fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapter =
            CountryAdapter(this, viewModel.stateFiltered, viewModel.countriesLiveData, object :
                CountryAdapter.OnCountryItemClickListener {
                override fun onClick(country: Country) {
                    country.alpha2Code?.run {
                        startActivity(
                            DetailsActivity.startIntent(
                                this@MainActivity,
                                this
                            )
                        )
                    }
                }
            })
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
        return true
    }

    private fun updateUi() {
        if (viewModel.stateLiveData.value == STATE.LOADING) {
            progress.visibility = View.VISIBLE
            error_message.visibility = View.GONE
            retry_button.visibility = View.GONE
            recycler_view.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
        }
        if (viewModel.stateFiltered.value != STATE.NO_RESULTS && viewModel.stateLiveData.value == STATE.LOADED) {
            error_message.visibility = View.GONE
            retry_button.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
        if (viewModel.stateFiltered.value == STATE.NO_RESULTS && viewModel.stateLiveData.value == STATE.LOADED) {
            error_message.visibility = View.VISIBLE
            error_message.text = getString(R.string.no_results_for_this_query)
            retry_button.visibility = View.GONE
            recycler_view.visibility = View.GONE
        }
        if (viewModel.stateLiveData.value == STATE.ERROR) {
            error_message.text = getString(R.string.error_fetching_data)
            error_message.visibility = View.VISIBLE
            retry_button.visibility = View.VISIBLE
        }

    }

}