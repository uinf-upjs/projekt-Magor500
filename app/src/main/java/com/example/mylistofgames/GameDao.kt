package com.example.mylistofgames

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames() : List<Game>

    @Query("SELECT * FROM game WHERE idGame = :id")
    fun getGameById(id: Int) : Game

    @Insert//(onConflict = OnConflictStrategy.REPLACE )
    fun insertGame(game: Game)

    @Insert//(onConflict = OnConflictStrategy.REPLACE )
    fun insertGames(game: List<Game>)
}