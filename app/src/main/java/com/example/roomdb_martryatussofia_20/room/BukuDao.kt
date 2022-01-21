package com.example.roomdb_martryatussofia_20.room

import androidx.room.*
//dao data akses objek
@Dao
interface BukuDao {

    @Insert
    suspend fun addBuku(book: Buku)

    @Update
    suspend fun updateBuku(buku: Buku)

    @Delete
    suspend fun deleteBuku(buku: Buku)

    @Query ("SELECT * FROM buku")
    suspend fun getbukuu():List<Buku>

    @Query ("SELECT * FROM buku WHERE id=:buku_id")
    suspend fun getbuku(buku_id: Int): List<Buku>

}