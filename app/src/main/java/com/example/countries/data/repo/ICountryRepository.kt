package com.example.countries.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.countries.data.models.Country

interface ICountryRepository {
    fun getAllCountries(livedata: MutableLiveData<List<Country>>)
}