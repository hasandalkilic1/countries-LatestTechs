package com.example.countries.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countries.data.models.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    suspend fun allCountries(): List<Country>

    @Query("SELECT * FROM countries WHERE country_name like '%' || :searchWord || '%'")
    suspend fun searchCountry(searchWord: String): List<Country>

    @Insert
    suspend fun addCountry(country: Country)

    @Query("DELETE FROM countries")
    suspend fun clearAllTable()
}