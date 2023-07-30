package com.example.countries.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.countries.data.models.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun getCountryDaoRoom(): CountryDao
}