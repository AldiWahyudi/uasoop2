package com.example.crudapp.Database

import androidx.room.*

@Dao
interface SiswaDao {
    @Insert
    suspend fun addHelm(siswa: Siswa)

    @Update
    suspend fun updateHelm(siswa: Siswa)

    @Delete
    suspend fun deleteHelm(siswa: Siswa)

    @Query("SELECT * FROM siswa")
    suspend fun getAllHelm(): List<Siswa>

    @Query("SELECT * FROM siswa WHERE id=:helm_id")
    suspend fun getHelm(helm_id: Int) : List<Siswa>

}