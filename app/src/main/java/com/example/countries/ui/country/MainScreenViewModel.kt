package com.example.countries.ui.country

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.data.models.Country
import com.example.countries.data.repo.LocalCountryRepository
import com.example.countries.data.repo.RemoteCountryRepository
import com.example.countries.utils.SharedPreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private var remoteCountryRepository: RemoteCountryRepository,
    var localCountryRepository: LocalCountryRepository
) : ViewModel() {
    var countryList = MutableLiveData<List<Country>>()

    init {
        getAllCountries()
    }

    fun search(searchWord: String) {
        localCountryRepository.countrySearch(searchWord, countryList)
    }

    fun getAllCountries() {
        val currentTime = Calendar.getInstance().timeInMillis

        val dateOfGetService =
            SharedPreUtil().getString("dateOfGetService", "", remoteCountryRepository.context)

        if (dateOfGetService.isEmpty()) {
            remoteCountryRepository.getAllCountries(countryList)
        } else if ((currentTime - dateOfGetService.toLong()) < 60 * 60 * 1000) {
            localCountryRepository.getAllCountries(countryList)
        } else if ((currentTime - dateOfGetService.toLong()) >= 60 * 60 * 1000) {
            remoteCountryRepository.getAllCountries(countryList)
        }
        if (countryList.value.isNullOrEmpty()) {
            countryList.value = remoteCountryRepository.getMockData()
        }
    }
}