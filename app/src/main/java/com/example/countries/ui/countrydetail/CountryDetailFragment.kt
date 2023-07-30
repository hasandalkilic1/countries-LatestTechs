package com.example.countries.ui.countrydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.countries.R
import com.example.countries.databinding.FragmentCountryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailFragment : Fragment() {

    private lateinit var binding: FragmentCountryDetailBinding
    private lateinit var viewModel: CountryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CountryDetailViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_detail, container, false)

        val bundle: CountryDetailFragmentArgs by navArgs()
        val country = bundle.country
        binding.countryDetailFragment = this
        binding.toolbarCountryTitle = country.country_name
        binding.country = country

        setUpActionBar()
        return binding.root
    }

    private fun setUpActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCountryDetail)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp)

        binding.toolbarCountryDetail.setNavigationOnClickListener { view ->
            requireActivity().onBackPressed()
        }
    }
}