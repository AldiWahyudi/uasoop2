package com.example.crudapp.Database

import androidx.room.*

@Dao
interface GuruDao {
    @Insert
    suspend fun addUser(guru: Guru)

    @Update
    suspend fun updateUser(guru: Guru)

    @Delete
    suspend fun deleteUser(guru: Guru)

    @Query("SELECT * FROM guru")
    suspend fun getAllUser(): List<Guru>

    @Query("SELECT * FROM guru WHERE id=:user_id")
    suspend fun getUser(user_id: Int) : List<Guru>
}