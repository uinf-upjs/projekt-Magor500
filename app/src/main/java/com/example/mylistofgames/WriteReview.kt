package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread


class WriteReview : AppCompatActivity() {

    var gameId: Int = 0
    var idActiveUser: Int = 0
    private lateinit var reviewDao: ReviewDao
    private lateinit var gameDao: GameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_review_list)

        gameId = intent.getIntExtra("gameId", 0);
        idActiveUser = intent.getIntExtra("activeUserId", 0)

        var textView = findViewById<TextView>(R.id.text_view_usernameR)
        var editText = findViewById<EditText>(R.id.editTextReview)
        var buttonW = findViewById<Button>(R.id.writeW)
        var buttonC = findViewById<Button>(R.id.cancelB)



        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)
            reviewDao = database.reviewDao()
            gameDao = database.gameDao()

            var review = reviewDao.getReviewForUser(idActiveUser, gameId)
            var game = gameDao.getGameById(gameId)

            withContext(Dispatchers.Main) {
                textView.text = game.name

                if (review != null) {
                    editText.setText(review!!.review)
                }
            }
        }


        buttonW.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                var toast = false

                var review = reviewDao.getReviewForUser(idActiveUser, gameId)

                if (review != null) {
                    if (editText.text.toString() != ""){
                        review.review = editText.text.toString()
                        reviewDao.insertReview(review)
                    }else{
                        toast = true
                    }

                } else {
                    if (editText.text.toString() != ""){
                        reviewDao.insertReview(
                            Review(0, idActiveUser, gameId, editText.text.toString())
                        )
                    }else{
                        toast = true
                    }
                }

                if(toast){
                    withContext(Dispatchers.Main){
                        val toast = Toast.makeText(applicationContext,"Cant write empty review", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }

            val intent = Intent(this, GameProfiel::class.java)
            intent.putExtra("gameId", gameId)
            intent.putExtra("activeUserId", idActiveUser)
            startActivity(intent)
            this.finish()
        }

            buttonC.setOnClickListener{
                val intent = Intent(this,GameProfiel::class.java)
                intent.putExtra("gameId",gameId)
                intent.putExtra("activeUserId",idActiveUser)
                startActivity(intent)
                this.finish()
            }
    }
}