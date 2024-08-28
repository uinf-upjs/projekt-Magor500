package com.example.mylistofgames

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntryGameDao {
    @Query("SELECT (SUM(rating)/COUNT(idEG)) FROM entryGame WHERE idGame=:id")
    fun getRating(id: Int) : Int

    @Query("SELECT * FROM entryGame WHERE idGame=:idG AND idUser = :id")
    fun getUserEntryGame(id: Int,idG: Int) : EntryGame

    @Query("SELECT * FROM entryGame WHERE idUser =:id")
    fun getAllUserEntry(id: Int) : List<EntryGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertEntryGame(game: EntryGame)

    @Query("SELECT COUNT(idEG) FROM entryGame WHERE idUser = :id AND status= :status ")
    fun getAllUserStatusGames(id: Int,status: String) : Int

    @Query("SELECT SUM(hoursSpent) FROM entryGame WHERE idUser = :id ")
    fun getAllUserHoursSpent(id: Int) : Int
}