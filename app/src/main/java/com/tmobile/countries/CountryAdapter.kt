package com.tmobile.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.tmobile.countries.model.Country
import com.tmobile.countries.model.STATE
import java.util.*

@Suppress("UNCHECKED_CAST")
internal class CountryAdapter(
    activity: MainActivity,
    stateFilteredLiveData: MutableLiveData<STATE>,
    countriesLiveData: MutableLiveData<List<Country>>,
    val itemClickListener: OnCountryItemClickListener
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), Filterable {

    interface OnCountryItemClickListener{
        fun onClick(country: Country)
    }

    private val countryList = ArrayList<Country>()
    private var countryListFull = ArrayList<Country>()

    init {
        countriesLiveData.observe(activity,
            Observer { t ->
                countryListFull.clear()
                t?.run {
                    countryListFull.addAll(this)
                    notifyDataSetChanged()
                }
            })
    }

    internal inner class CountryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image_view)
        var textView1: TextView = itemView.findViewById(R.id.text_view1)
        var root: View = itemView.findViewById(R.id.root)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.country_item,
            parent, false
        )
        return CountryViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        val currentItem: Country = countryList[position]
        SvgLoader.pluck()
            .with(holder.imageView.context as MainActivity)
            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
            .load(currentItem.flag, holder.imageView)
        holder.textView1.text = currentItem.name
        holder.root.setOnClickListener { itemClickListener.onClick(currentItem)}
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun getFilter(): Filter {
        return countryFilter
    }

    private val countryFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Country> =
                ArrayList<Country>()
            if (constraint.isEmpty()) {
                filteredList.addAll(countryListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.US).trim { it <= ' ' }
                for (item in countryListFull) {
                    if (item.name?.toLowerCase(Locale.US)?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            countryList.clear()
            countryList.addAll(results.values as List<Country>)
            if (countryList.isEmpty())
                stateFilteredLiveData.postValue(STATE.NO_RESULTS)
            else
                stateFilteredLiveData.postValue(STATE.RESULTS_AVAILABLE)
            notifyDataSetChanged()
        }
    }
}