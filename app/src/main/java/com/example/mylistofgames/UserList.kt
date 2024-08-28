package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import kotlin.concurrent.thread

class UserList : AppCompatActivity() {
    private lateinit var userDao: UserDao
    var idActiveUser: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_list)

        idActiveUser = intent.getIntExtra("activeUserId",0)

        var userListView = findViewById<ListView>(R.id.userListView)

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

        Thread {
            val database = AppDatabase.getDatabase(this)
            userDao = database.userDao()

            var listNames = userDao.getAllUsernames()
            var listId = userDao.getAllId()

            var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames)
            userListView.adapter = arrayAdapter

            userListView.setOnItemClickListener{
                    adapterView, view, position, id ->

                runOnUiThread{
                    val intent = Intent(this,Profile::class.java)
                    intent.putExtra("userId",listId[position])
                    intent.putExtra("activeUserId", idActiveUser)
                    startActivity(intent)
                    this.finish()
                }

            }

        }.start()
    }
}