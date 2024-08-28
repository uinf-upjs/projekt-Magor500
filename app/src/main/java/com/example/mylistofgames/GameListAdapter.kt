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

class GameListAdapter(val context: Context): BaseAdapter() {


    val database = AppDatabase.getDatabase(context)
    private var gameDao: GameDao = database.gameDao()
    val allGames = mutableListOf<Game>()

    init{
        CoroutineScope(Dispatchers.IO).launch {
            allGames.addAll( gameDao.getAllGames())
            withContext(Dispatchers.Main){
                notifyDataSetChanged()
            }
        }
    }


    override fun getCount(): Int {
        return allGames.size;
    }

    override fun getItem(p0: Int): Any {
        return allGames[p0]
    }

    override fun getItemId(p0: Int): Long {
        return allGames[p0].idGame.toLong()

    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val rowView = inflater.inflate(R.layout.activity_game_list,null,true)
        val imageView: ImageView = rowView.findViewById(R.id.photo)
        val textView: TextView = rowView.findViewById(R.id.text_view_game)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                textView.text = allGames[position].name
                Glide.with(context).load(allGames[position].photo).into(imageView)
            }
        }




        return rowView
    }
}