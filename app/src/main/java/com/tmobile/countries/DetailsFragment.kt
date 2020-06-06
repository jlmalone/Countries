package com.tmobile.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmobile.countries.databinding.DetailsFragmentBinding
import java.util.*

//import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailsFragment : Fragment() {
//    lateinit var viewModel: CountryDetailsViewModel
    lateinit var detailsFragmentBinding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.details_fragment,
            container,
            false
        )
        detailsFragmentBinding.lifecycleOwner = this
        detailsFragmentBinding.detailsViewModel =
            ViewModelProvider(requireActivity()).get(CountryDetailsViewModel::class.java)
        return detailsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }



}