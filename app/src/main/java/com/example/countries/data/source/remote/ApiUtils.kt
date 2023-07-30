package com.example.countries.data.source.remote

class ApiUtils {
    companion object {
        val BASE_URL = "http://IP_ADRESS"

        fun getCountriesDao(): CountryRemote {
            return RetrofitClient.getClient(BASE_URL).create(CountryRemote::class.java)
        }
    }
}