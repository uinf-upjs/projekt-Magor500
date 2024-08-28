package com.example.mylistofgames

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idGame") val idGame: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "releaseDate") val releaseDate : String,
    @ColumnInfo(name = "developer") val developer : String,
    @ColumnInfo(name = "photo") val photo: String,
)
