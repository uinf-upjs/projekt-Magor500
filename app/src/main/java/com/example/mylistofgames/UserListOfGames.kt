package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomappbar.BottomAppBar

class UserListOfGames : AppCompatActivity() {
    var idActiveUser: Int = 0
    var idUser: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_list_of_games)
        idActiveUser = intent.getIntExtra("activeUserId",0)
        idUser = intent.getIntExtra("userId",0)
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
                    val intent = Intent(this,UserGameListAdapter::class.java)
                    intent.putExtra("activeUserId",idActiveUser)
                    startActivity(intent)
                    this.finish()
                    true
                }
                else -> false
            }
        }

        Thread {
            var userGameListAdapter : UserGameListAdapter


            if(idUser == 0){
                userGameListAdapter = UserGameListAdapter(this, idActiveUser)
            }else{
                userGameListAdapter = UserGameListAdapter(this, idUser)
            }

            runOnUiThread {  gameList.adapter = userGameListAdapter }


            gameList.setOnItemClickListener{ adapterView, view, position, id ->
                if(idUser == 0){
                    val gameId : Long = adapterView.getItemIdAtPosition(position)

                    runOnUiThread{
                        val intent = Intent(this,AddActivity::class.java)
                        intent.putExtra("gameId", gameId.toInt());
                        intent.putExtra("activeUserId",idActiveUser)
                        startActivity(intent)
                        this.finish()
                    }
                }else{
                    val gameId : Long = adapterView.getItemIdAtPosition(position)

                    runOnUiThread{
                        val intent = Intent(this,GameProfiel::class.java)
                        intent.putExtra("gameId", gameId.toInt());
                        intent.putExtra("activeUserId",idActiveUser)
                        startActivity(intent)
                        this.finish()
                    }
                }
            }

        }.start()
    }
}