package com.example.mylistofgames

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUser") val idUser: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "username") val username : String,
    @ColumnInfo(name = "passwd") val passwd : String
)
