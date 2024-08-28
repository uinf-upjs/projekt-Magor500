package com.example.mylistofgames

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomappbar.BottomAppBar
import org.w3c.dom.Text

class Profile : AppCompatActivity() {
    var idActiveUser: Int = 0
    var idUser: Int = 0
    private lateinit var entryGameDao: EntryGameDao
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        idActiveUser = intent.getIntExtra("activeUserId",0)
        idUser = intent.getIntExtra("userId",0)

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

        var profleName = findViewById<TextView>(R.id.profileUserName)

        var completedView = findViewById<TextView>(R.id.completedCount)
        var playingView = findViewById<TextView>(R.id.playingCount)
        var onholdView = findViewById<TextView>(R.id.onholdCount)
        var wanttoplayView = findViewById<TextView>(R.id.wanttoplayCount)
        var droppedView = findViewById<TextView>(R.id.droppedCount)
        var hoursSpentView = findViewById<TextView>(R.id.totalHours)

        var button = findViewById<Button>(R.id.checkId)
        var reviewList = findViewById<ListView>(R.id.reviewListViewP)

        Thread{
            val database = AppDatabase.getDatabase(this)
            entryGameDao = database.entryGameDao()
            userDao = database.userDao()
            var name: String
            var profielReviewListAdapter: ProfielReviewListAdapter
            if (idUser == 0) {


                var countC = entryGameDao.getAllUserStatusGames(idActiveUser,"Completed")
                name = userDao.getById(idActiveUser).username
                var countP = entryGameDao.getAllUserStatusGames(idActiveUser,"Playing")
                var countO = entryGameDao.getAllUserStatusGames(idActiveUser,"On hold")
                var countW = entryGameDao.getAllUserStatusGames(idActiveUser,"Want to play")
                var countD = entryGameDao.getAllUserStatusGames(idActiveUser,"Dropped")
                var hoursS = entryGameDao.getAllUserHoursSpent(idActiveUser)

                runOnUiThread {
                    completedView.text = countC.toString()
                    profleName.text = name
                    playingView.text = countP.toString()
                    onholdView.text = countO.toString()
                    wanttoplayView.text = countW.toString()
                    droppedView.text = countD.toString()
                    hoursSpentView.text = hoursS.toString()
                }



                    profielReviewListAdapter = ProfielReviewListAdapter(this,idActiveUser)




            }else{

                var countC = entryGameDao.getAllUserStatusGames(idUser,"Completed")
                name = userDao.getById(idUser).username
                var countP = entryGameDao.getAllUserStatusGames(idUser,"Playing")
                var countO = entryGameDao.getAllUserStatusGames(idUser,"On hold")
                var countW = entryGameDao.getAllUserStatusGames(idUser,"Want to play")
                var countD = entryGameDao.getAllUserStatusGames(idUser,"Dropped")
                var hoursS = entryGameDao.getAllUserHoursSpent(idUser)

                runOnUiThread {
                    completedView.text = countC.toString()
                    profleName.text = name
                    playingView.text = countP.toString()
                    onholdView.text = countO.toString()
                    wanttoplayView.text = countW.toString()
                    droppedView.text = countD.toString()
                    hoursSpentView.text = hoursS.toString()
                }

                profielReviewListAdapter = ProfielReviewListAdapter(this,idUser)
                reviewList.adapter = profielReviewListAdapter

            }

            reviewList.adapter = profielReviewListAdapter


        }.start()

        button.setOnClickListener {
            if (idUser == 0) {

                val intent = Intent(this, UserListOfGames::class.java)
                intent.putExtra("activeUserId", idActiveUser)
                startActivity(intent)
                this.finish()

            }else{

                val intent = Intent(this, UserListOfGames::class.java)
                intent.putExtra("activeUserId", idActiveUser)
                intent.putExtra("userId", idUser)
                startActivity(intent)
                this.finish()

            }
        }



    }
}