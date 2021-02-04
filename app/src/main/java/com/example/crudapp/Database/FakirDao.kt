package com.example.crudapp.Database

import androidx.room.*

@Dao
interface FakirDao {
    @Insert
    suspend fun addFakir(fakir: Fakir)

    @Update
    suspend fun updateFakir(fakir: Fakir)

    @Delete
    suspend fun deleteFakir(fakir: Fakir)

    @Query("SELECT * FROM fakir")
    suspend fun getAllFakir(): List<Fakir>

    @Query("SELECT * FROM fakir WHERE id=:fakir_id")
    suspend fun getFakir(fakir_id: Int) : List<Fakir>

}