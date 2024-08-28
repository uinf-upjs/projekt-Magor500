package com.example.mylistofgames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewListAdapter (val context: Context, val id :Int): BaseAdapter() {

    val database = AppDatabase.getDatabase(context)
    var reviewDao: ReviewDao = database.reviewDao()
    var userDao: UserDao = database.userDao()
    val allReview = mutableListOf<Review>()

    init{
        CoroutineScope(Dispatchers.IO).launch {
            allReview.addAll( reviewDao.getAllReviewForGame(id))
            withContext(Dispatchers.Main){
                notifyDataSetChanged()
            }
        }
    }

    override fun getCount(): Int {
       return allReview.size
    }

    override fun getItem(p0: Int): Any {
        return allReview[p0]
    }

    override fun getItemId(p0: Int): Long {
        return allReview[p0].idReview.toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val rowView = inflater.inflate(R.layout.review_list,null,true)
        val textViewU: TextView = rowView.findViewById(R.id.text_view_usernameR)
        val textViewR: TextView = rowView.findViewById(R.id.text_view_review)

        CoroutineScope(Dispatchers.IO).launch {
            var username = userDao.getById(allReview[position].idUser).username
            withContext(Dispatchers.Main) {
                textViewU.text = username
                textViewR.text = allReview[position].review
            }
        }


        return rowView
    }
}