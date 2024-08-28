package com.example.mylistofgames

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = :emails")
    fun getByEmail(emails: String): User

    @Query("SELECT * FROM user WHERE username = :username")
    fun getByUsername(username: String): User

    @Query("SELECT * FROM user WHERE idUser = :id")
    fun getById(id: Int): User

    @Query("SELECT username FROM user")
    fun getAllUsernames(): List<String>

    @Query("SELECT idUser FROM user")
    fun getAllId(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}