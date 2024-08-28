package com.example.mylistofgames

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "review",
        foreignKeys = [
    androidx.room.ForeignKey(
        entity = com.example.mylistofgames.User::class,
        parentColumns = kotlin.arrayOf("idUser"),
        childColumns = kotlin.arrayOf("idUser"),
        onDelete = androidx.room.ForeignKey.NO_ACTION
    ),
    androidx.room.ForeignKey(
        entity = com.example.mylistofgames.Game::class,
        parentColumns = kotlin.arrayOf("idGame"),
        childColumns = kotlin.arrayOf("idGame"),
        onDelete = androidx.room.ForeignKey.NO_ACTION
    )
])
data class Review(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idReview") val idReview: Int,
    @ColumnInfo(name = "idUser") val idUser: Int,
    @ColumnInfo(name = "idGame") val idGame: Int,
    @ColumnInfo(name = "review") var review: String,
)
