package com.example.countries.ui.country

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.data.models.Country
import com.example.countries.databinding.CardDesignBinding
import com.example.countries.utils.transition

class CountryAdapter(
    var context: Context,
    var countryList: List<Country>,
    var viewModel: MainScreenViewModel
) : RecyclerView.Adapter<CountryAdapter.MViewHolder>() {
    inner class MViewHolder(binding: CardDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardDesignBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: CardDesignBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.card_design, parent, false)
        return MViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val country = countryList[position]
        val binding = holder.binding

        binding.country = country

        binding.cardLine.setOnClickListener { view ->
            val transition = MainScreenFragmentDirections.detailTransition(country = country)
            Navigation.transition(view, transition)
        }

    }
}