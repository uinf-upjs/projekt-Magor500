package com.example.mylistofgames

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    //private lateinit var gameDao: GameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        var username = findViewById<EditText>(R.id.username_text_input)
        var passwd = findViewById<EditText>(R.id.passwd_text_input)

        var loginButton : Button = findViewById(R.id.button_login);
        loginButton.setOnClickListener(){
            val database = AppDatabase.getDatabase(this)
            userDao = database.userDao()
            //gameDao = database.gameDao()

            login(username.text.toString(),passwd.text.toString())

        }

        var signUpButton : Button = findViewById(R.id.button_signUp);
        signUpButton.setOnClickListener(){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            this.finish()
        }
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }

   /* val game = listOf(
        Game(0,"Elden Ring", "2022-02-25","FromSoftware","https://upload.wikimedia.org/wikipedia/en/b/b9/Elden_Ring_Box_art.jpg"),
        Game(0,"God Of War", "2018-01-14","Santa Monica Studio","https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg"),
        Game(0,"Lies of P", "2023-09-18","Neowiz Games, Round8 Studio", "https://upload.wikimedia.org/wikipedia/en/d/de/Lies_of_p_cover_art.jpg"),
        Game(0,"Ghost of Tsushima", "2020-07-17","Sucker Punch Productions", "https://upload.wikimedia.org/wikipedia/en/b/b6/Ghost_of_Tsushima.jpg"),
        Game(0,"Sonic Adventure","1998-12-23","Sonic Team","https://upload.wikimedia.org/wikipedia/en/6/60/Sonic_Adventure.PNG"),
        Game(0,"Half-Life","1998-11-19","Valve","https://upload.wikimedia.org/wikipedia/en/f/fa/Half-Life_Cover_Art.jpg"),
        Game(0,"Half-Life 2","2004-11-16","Valve","https://upload.wikimedia.org/wikipedia/en/2/25/Half-Life_2_cover.jpg"),
        Game(0,"Resident Evil 4","2005-01-11", "Capcom Production Studio 4" , "https://upload.wikimedia.org/wikipedia/en/d/d9/Resi4-gc-cover.jpg")
    )*/


    fun login(email: String, passwd: String){
        Thread {
            var user: User? = userDao.getByEmail(email);
           // gameDao.insertGames(game)
            if(user != null && user.passwd == passwd){
                runOnUiThread{
                    val toast = Toast.makeText(this,"Logged in", Toast.LENGTH_SHORT)
                    toast.show()
                    val intent = Intent(this,ListOfGames::class.java)
                    intent.putExtra("activeUserId", user.idUser);
                    startActivity(intent)
                    this.finish()
                }
            }else{
                runOnUiThread{
                    val toast = Toast.makeText(this,"Wrong email or password", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }.start()
    }
}