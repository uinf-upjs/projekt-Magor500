package com.example.mylistofgames

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review WHERE idGame = :id")
    fun getAllReviewForGame(id: Int) : List<Review>

    @Query("SELECT * FROM review WHERE idUser = :id")
    fun getAllReviewForUser(id: Int) : List<Review>

    @Query("SELECT * FROM review WHERE idUser = :id AND idGame = :idG")
    fun getReviewForUser(id: Int, idG: Int) : Review

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertReview(review: Review)

    @Update
    fun updateReview(review: Review)
}