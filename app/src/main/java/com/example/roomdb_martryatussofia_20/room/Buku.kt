package com.example.roomdb_martryatussofia_20.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Buku (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title : String,
    val desc: String,
    )