package com.example.countries.data.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.countries.data.models.Country
import com.example.countries.data.models.CountryAnswer
import com.example.countries.data.source.local.CountryDao
import com.example.countries.data.source.remote.CountryRemote
import com.example.countries.utils.SharedPreUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.Calendar

class RemoteCountryRepository(
    var countryRemote: CountryRemote,
    var countryDao: CountryDao,
    var context: Context
) : ICountryRepository {
    fun addCountryToDb(
        country_name: String,
        capital_name: String,
        country_flag: String,
        country_description: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val newCountry =
                Country(0, country_name, capital_name, country_flag, country_description)
            countryDao.addCountry(newCountry)
        }
    }

    override fun getAllCountries(liveData: MutableLiveData<List<Country>>) {
        countryRemote.allCountries().enqueue(object : Callback<CountryAnswer> {
            override fun onResponse(
                call: Call<CountryAnswer>?,
                response: Response<CountryAnswer>
            ) {
                val list = response.body().countries
                liveData.postValue(list)

                CoroutineScope(Dispatchers.IO).launch {
                    countryDao.clearAllTable()
                }

                for (i in list) {
                    addCountryToDb(
                        i.country_name,
                        i.capital_name,
                        i.country_flag,
                        i.country_description
                    )
                }

                val currentTime = Calendar.getInstance().timeInMillis

                val sharedPref = SharedPreUtil()
                sharedPref.putString("dateOfGetService", currentTime.toString(), context)
                liveData.postValue(list)
            }

            override fun onFailure(call: Call<CountryAnswer>?, t: Throwable?) {
                CoroutineScope(Dispatchers.IO).launch {
                    liveData.postValue(countryDao.allCountries())
                }
            }
        })
    }

    fun getMockData(): List<Country> {
        val list = ArrayList<Country>()
        list.add(
            Country(
                0,
                "Turkey",
                "Ankara",
                "https://cdn.countryflags.com/thumbs/turkey/flag-800.png",
                "..."
            )
        )

        for (i in list) {
            addCountryToDb(
                i.country_name, i.capital_name, i.country_flag, i.country_description
            )
        }

        return list
    }
}
