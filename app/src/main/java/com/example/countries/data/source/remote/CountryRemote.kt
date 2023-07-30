package com.example.countries.data.source.remote

import com.example.countries.data.models.CountryAnswer
import retrofit2.Call
import retrofit2.http.GET

interface CountryRemote {
    @GET("countries")
    fun allCountries(): Call<CountryAnswer>
}