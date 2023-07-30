package com.example.countries.data.models

import com.google.gson.annotations.SerializedName

data class CountryAnswer(
    @SerializedName("countries") var countries: List<Country>
) {}