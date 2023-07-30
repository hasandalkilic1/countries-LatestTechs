package com.example.countries.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull


@Entity(tableName = "countries")
@Parcelize
data class Country(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) @NotNull var id: Int,
    @ColumnInfo(name = "country_name") @NotNull var country_name: String,
    @ColumnInfo(name = "capital_name") @NotNull var capital_name: String,
    @ColumnInfo(name = "country_flag") @NotNull var country_flag: String,
    @ColumnInfo(name = "country_description") @NotNull var country_description: String
) : Parcelable {
    private companion object : Parceler<Country> {
        override fun create(parcel: Parcel): Country {
            return Country(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!
            )
        }

        override fun Country.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(country_name)
            parcel.writeString(capital_name)
            parcel.writeString(country_flag)
            parcel.writeString(country_description)
        }

    }
}
