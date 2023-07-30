package com.example.countries.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.countries.data.models.Country
import com.example.countries.data.source.local.CountryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalCountryRepository(var countryDao: CountryDao) : ICountryRepository {
    fun countrySearch(searchWord: String, livedata: MutableLiveData<List<Country>>) {
        CoroutineScope(Dispatchers.IO).launch {
            livedata.postValue(countryDao.searchCountry(searchWord))
        }
    }

    override fun getAllCountries(livedata: MutableLiveData<List<Country>>) {
        CoroutineScope(Dispatchers.IO).launch {
            livedata.postValue(countryDao.allCountries())
        }
    }
}