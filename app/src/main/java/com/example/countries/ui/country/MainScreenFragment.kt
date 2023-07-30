package com.example.countries.ui.country

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.countries.R
import com.example.countries.databinding.FragmentMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var viewModel: MainScreenViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)
        binding.toolbarMainScreenTitle = "Countries"

        setUpActionBar()

        setAdapter()

        return binding.root
    }

    private fun setAdapter() {
        viewModel.countryList.observe(viewLifecycleOwner) { countryList ->
            CountryAdapter(requireContext(), countryList, viewModel).apply {
                notifyDataSetChanged()
                binding.countryAdapter = this
            }
        }
    }

    private fun setUpActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarMainScreen)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_main_screen_menu, menu)

                val item = menu.findItem(R.id.action_search)
                searchView = item.actionView as SearchView

                searchView.setOnQueryTextListener(this@MainScreenFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MainScreenViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return if (newText.length <= 50) {
            viewModel.search(newText)
            true
        } else {
            searchView.setQuery(newText.substring(0, 50), false)
            true
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCountries()
    }

}