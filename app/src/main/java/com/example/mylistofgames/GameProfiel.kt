package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomappbar.BottomAppBar

class GameProfiel : AppCompatActivity() {
    var gameId: Int = 0
    var idActiveUser: Int = 0
    private lateinit var gameDao: GameDao
    private lateinit var entryGameDao: EntryGameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_profiel)

        gameId = intent.getIntExtra("gameId", 0);
        idActiveUser = intent.getIntExtra("activeUserId",0)

        var bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    intent.putExtra("activeUserId", idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }

                R.id.browse -> {
                    val intent = Intent(this, ListOfGames::class.java)
                    intent.putExtra("activeUserId", idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }

                R.id.users -> {
                    val intent = Intent(this, UserList::class.java)
                    intent.putExtra("activeUserId", idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }

                R.id.myList -> {
                    val intent = Intent(this, UserListOfGames::class.java)
                    intent.putExtra("activeUserId", idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }

                else -> false
            }
        }

        var icon = findViewById<ImageView>(R.id.icon)
        var gameName = findViewById<TextView>(R.id.text_view_gameName)
        var developer = findViewById<TextView>(R.id.text_view_gameDeveloper)
        var rating = findViewById<TextView>(R.id.text_view_gameRating)

        var addButton = findViewById<Button>(R.id.button_addToList)
        var writeButton = findViewById<Button>(R.id.button_writeReview)
        var reviewListView = findViewById<ListView>(R.id.reviewListView)

        Thread{
            val database = AppDatabase.getDatabase(this)
            gameDao = database.gameDao()
            var game = gameDao.getGameById(gameId)

            entryGameDao = database.entryGameDao()
            var ratingGame: Int = entryGameDao.getRating(gameId)

            runOnUiThread{
                Glide.with(this).load(game.photo).into(icon)
                gameName.text = game.name
                developer.text = game.developer
                if(ratingGame == 0){
                    rating.text = "N/A"
                }else{
                    rating.text = ratingGame.toString()
                }
            }

            val reviewListAdapter = ReviewListAdapter(this,gameId)
            reviewListView.adapter = reviewListAdapter
        }.start()


        addButton.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("gameId",gameId)
            intent.putExtra("activeUserId",idActiveUser)
            startActivity(intent)
            this.finish()
        }

        writeButton.setOnClickListener {
            val intent = Intent(this,WriteReview::class.java)
            intent.putExtra("gameId",gameId)
            intent.putExtra("activeUserId",idActiveUser)
            startActivity(intent)
            this.finish()
        }

    }
}