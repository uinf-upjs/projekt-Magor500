package com.example.mylistofgames

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "entryGame",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("idUser"),
            childColumns = arrayOf("idUser"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Game::class,
            parentColumns = arrayOf("idGame"),
            childColumns = arrayOf("idGame"),
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class EntryGame(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEG") val idEG: Int,
    @ColumnInfo(name = "idUser") val idUser: Int,
    @ColumnInfo(name = "idGame") val idGame: Int,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "startDate") var startDate: String,
    @ColumnInfo(name = "endDate") var endDate: String,
    @ColumnInfo(name = "rating") var rating: Int,
    @ColumnInfo(name = "hoursSpent") var hoursSpent: Int,
)
