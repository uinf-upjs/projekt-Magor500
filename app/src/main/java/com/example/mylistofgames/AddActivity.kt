package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class AddActivity : AppCompatActivity() {
    var gameId: Int = 0
    var idActiveUser: Int = 0

    private lateinit var gameDao: GameDao
    private lateinit var entryGameDao: EntryGameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        gameId = intent.getIntExtra("gameId", 0);
        idActiveUser = intent.getIntExtra("activeUserId",0)

        val statuses = resources.getStringArray(R.array.Status)
        val ratings = resources.getStringArray(R.array.Rating)

        var rating = ""
        var status = ""

        val spinnerS = findViewById<Spinner>(R.id.status_spinner)
        if(spinnerS != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
            spinnerS.adapter = adapter

            spinnerS.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    status = statuses[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    status = ""
                }
            }

        }

        val spinnerR = findViewById<Spinner>(R.id.rating_spinner)
        if(spinnerR != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ratings)
            spinnerR.adapter = adapter

            spinnerR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    rating = ratings[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    rating = ""
                }
            }
        }

        var name = findViewById<TextView>(R.id.text_view_name)
        val startDate = findViewById<EditText>(R.id.edit_text_startDate)
        val endDate = findViewById<EditText>(R.id.edit_text_endDate)
        val hoursSpent = findViewById<EditText>(R.id.edit_text_hoursSpent)
        val buttonAdd = findViewById<Button>(R.id.button_add)

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            gameDao = database.gameDao()
            entryGameDao = database.entryGameDao()

            var entryGame = entryGameDao.getUserEntryGame(idActiveUser,gameId)
            var game = gameDao.getGameById(gameId)

            withContext(Dispatchers.Main){
                name.setText(game.name)
                if(entryGame != null){
                    startDate.setText(entryGame.startDate)
                    endDate.setText(entryGame.endDate)
                    hoursSpent.setText(entryGame.hoursSpent.toString())

                    if (entryGame.status == "Completed") spinnerS.setSelection(statuses.indexOf("Completed"))
                    if (entryGame.status == "Playing") spinnerS.setSelection(statuses.indexOf("Playing"))
                    if (entryGame.status == "On hold") spinnerS.setSelection(statuses.indexOf("On hold"))
                    if (entryGame.status == "Want to play") spinnerS.setSelection(statuses.indexOf("Want to play"))
                    if (entryGame.status == "Dropped") spinnerS.setSelection(statuses.indexOf("Dropped"))

                    if (entryGame.rating == 1) spinnerR.setSelection(ratings.indexOf("1"))
                    if (entryGame.rating == 2) spinnerR.setSelection(ratings.indexOf("2"))
                    if (entryGame.rating == 3) spinnerR.setSelection(ratings.indexOf("3"))
                    if (entryGame.rating == 4) spinnerR.setSelection(ratings.indexOf("4"))
                    if (entryGame.rating == 5) spinnerR.setSelection(ratings.indexOf("5"))
                }
            }
        }


            buttonAdd.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {

                    val database = AppDatabase.getDatabase(applicationContext)
                    gameDao = database.gameDao()
                    entryGameDao = database.entryGameDao()

                    var entryGame = entryGameDao.getUserEntryGame(idActiveUser,gameId)

                if(status != "" || rating != "" || hoursSpent.text.toString() != ""){
                    if (entryGame != null){
                        entryGame.hoursSpent = hoursSpent.text.toString().toInt()
                        entryGame.status = status
                        entryGame.startDate = startDate.text.toString()
                        entryGame.endDate = endDate.text.toString()
                        entryGame.rating = rating.toInt()

                        entryGameDao.insertEntryGame(entryGame)
                    }else{
                        entryGameDao.insertEntryGame(EntryGame(0, idActiveUser, gameId,status,startDate.text.toString(),endDate.text.toString(), rating.toInt(),hoursSpent.text.toString().toInt()))
                    }

                    }else{

                        withContext(Dispatchers.Main){
                            val toast = Toast.makeText(applicationContext,"Status, rating and hours spent has to be not empty", Toast.LENGTH_SHORT)
                            toast.show()
                        }

                    }
                }

                val intent = Intent(this,GameProfiel::class.java)
                intent.putExtra("gameId",gameId)
                intent.putExtra("activeUserId",idActiveUser)
                startActivity(intent)
                this.finish()
            }





        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        buttonCancel.setOnClickListener{
            val intent = Intent(this,GameProfiel::class.java)
            intent.putExtra("gameId",gameId)
            intent.putExtra("activeUserId",idActiveUser)
            startActivity(intent)
            this.finish()
        }
    }


}