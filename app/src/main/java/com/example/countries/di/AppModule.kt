package com.example.countries.di

import android.content.Context
import androidx.room.Room
import com.example.countries.data.repo.LocalCountryRepository
import com.example.countries.data.repo.RemoteCountryRepository
import com.example.countries.data.source.local.CountryDao
import com.example.countries.data.source.local.CountryDatabase
import com.example.countries.data.source.remote.ApiUtils
import com.example.countries.data.source.remote.CountryRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalCountryRepository(
        countryDao: CountryDao
    ): LocalCountryRepository {
        return LocalCountryRepository(countryDao)
    }

    @Provides
    @Singleton
    fun provideRemoteCountryRepository(
        countryDao: CountryRemote,
        countryDaoRoom: CountryDao,
        @ApplicationContext context: Context
    ): RemoteCountryRepository {
        return RemoteCountryRepository(countryDao, countryDaoRoom, context)
    }

    @Provides
    @Singleton
    fun provideCountryDao(): CountryRemote {
        return ApiUtils.getCountriesDao()
    }

    @Provides
    @Singleton
    fun provideCountryDaoRoom(@ApplicationContext context: Context): CountryDao {
        val db = Room.databaseBuilder(context, CountryDatabase::class.java, "countries.sqlite")
            .createFromAsset("countries.sqlite").build()
        return db.getCountryDaoRoom()
    }
}