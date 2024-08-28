package com.example.mylistofgames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserGameListAdapter (val context: Context, val id : Int): BaseAdapter() {


    val database = AppDatabase.getDatabase(context)
    private var gameDao: GameDao = database.gameDao()
    private var entryGameDao: EntryGameDao = database.entryGameDao()
    val allEntryGames = mutableListOf<EntryGame>()

    init{
        CoroutineScope(Dispatchers.IO).launch {
            allEntryGames.addAll( entryGameDao.getAllUserEntry(id))
            withContext(Dispatchers.Main){
                notifyDataSetChanged()
            }
        }
    }

    override fun getCount(): Int {
        return allEntryGames.size;
    }

    override fun getItem(p0: Int): Any {
        return allEntryGames[p0]
    }

    override fun getItemId(p0: Int): Long {
        return allEntryGames[p0].idGame.toLong()

    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val rowView = inflater.inflate(R.layout.user_game_list,null,true)
        val imageView: ImageView = rowView.findViewById(R.id.photo)
        val textViewGame: TextView = rowView.findViewById(R.id.text_view_game)
        val textViewStatus: TextView = rowView.findViewById(R.id.text_view_gameStatus)
        val textViewRating: TextView = rowView.findViewById(R.id.text_view_gameRating)

        CoroutineScope(Dispatchers.IO).launch {
            var game = gameDao.getGameById(allEntryGames[position].idGame)
            withContext(Dispatchers.Main){
                textViewGame.text = game.name
                textViewStatus.text = allEntryGames[position].status
                textViewRating.text = allEntryGames[position].rating.toString()
                Glide.with(context).load(game.photo).into(imageView)
            }

        }

        return rowView
    }
}