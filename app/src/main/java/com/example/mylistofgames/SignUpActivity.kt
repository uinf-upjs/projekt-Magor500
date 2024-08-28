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

class SignUpActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)



        var email = findViewById<EditText>(R.id.emailS_text_input)
        var username = findViewById<EditText>(R.id.usernameS_text_input)
        var passwd = findViewById<EditText>(R.id.passwdS_text_input)
        var repeatPasswd = findViewById<EditText>(R.id.repeatpasswdS_text_input)

        var loginButton : Button = findViewById(R.id.button_loginS);
        loginButton?.setOnClickListener(){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        var signUpButton : Button = findViewById(R.id.button_signUpS);
        signUpButton?.setOnClickListener(){
        //thread {
            val database = AppDatabase.getDatabase(this)
            userDao = database.userDao()

            //lifecycleScope.launch {
            //runOnUiThread {
            signUp(
                email.text.toString(),
                username.text.toString(),
                passwd.text.toString(),
                repeatPasswd.text.toString()
                )
           // }
            //}
        //}




        }

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }

    fun signUp(email: String, username: String, passwd: String, repeatPasswd: String) {
        Thread {
            if (passwd == repeatPasswd) {
                if (userDao.getByEmail(email) == null){
                    if (userDao.getByUsername(username) == null){
                        var user = User(0, email, username, passwd)
                        userDao.insertUser(user)

                        runOnUiThread {
                            val toast = Toast.makeText(this, "Account created", Toast.LENGTH_SHORT)
                            toast.show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            this.finish()
                        }
                    }else{
                        runOnUiThread {

                            val toast = Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT)
                            toast.show()

                        }
                    }
                }else{
                    runOnUiThread {
                        val toast = Toast.makeText(this, "Email already taken", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }

            } else {
               runOnUiThread {
                    val toast = Toast.makeText(this, "Passwords have to match", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }.start()

    }
}
