package com.example.mylistofgames

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.sql.Date

@Database(entities = [User::class,Game::class,EntryGame::class,Review::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun entryGameDao(): EntryGameDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null



        fun getDatabase(context : Context) : AppDatabase{
            return INSTANCE ?:  synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mylistofgames_database"
                ).fallbackToDestructiveMigration()
                    .build()

                /*.addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Date.valueOf("--"),

                        INSTANCE.let {
                            Thread{
                                getDatabase(context).gameDao().insertGames(game)
                            }

                        }


                    }


                })*/

                INSTANCE = instance
                instance
            }
        }
    }
}