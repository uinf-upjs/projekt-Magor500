package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

class ListOfGames : AppCompatActivity() {
    var idActiveUser: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_of_games)
        idActiveUser = intent.getIntExtra("activeUserId",0)
        val gameList: ListView = findViewById(R.id.gameListView)

        var bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        bottomAppBar.setOnMenuItemClickListener {
            menuItem ->
            when(menuItem.itemId) {
                R.id.profile -> {
                    val intent = Intent(this,Profile::class.java)
                    intent.putExtra("activeUserId",idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }
                R.id.browse -> {
                    val intent = Intent(this,ListOfGames::class.java)
                    intent.putExtra("activeUserId",idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }
                R.id.users -> {
                    val intent = Intent(this,UserList::class.java)
                    intent.putExtra("activeUserId",idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }
                R.id.myList -> {
                    val intent = Intent(this,UserListOfGames::class.java)
                    intent.putExtra("activeUserId",idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }
                else -> false
            }
        }

        val gameListAdapter = GameListAdapter(this)
        gameList.adapter = gameListAdapter

        gameList.setOnItemClickListener{ adapterView, view, position, id ->
            val gameId : Long = adapterView.getItemIdAtPosition(position)


                val intent = Intent(this,GameProfiel::class.java)
                intent.putExtra("gameId", gameId.toInt());
                intent.putExtra("activeUserId",idActiveUser)
                startActivity(intent)
                this.finish()




        }





    }
}