package com.example.crudapp.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fakir")
data class Fakir(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "alamat") val alamat: String,
    @ColumnInfo(name = "jumlah") val no: Int
)