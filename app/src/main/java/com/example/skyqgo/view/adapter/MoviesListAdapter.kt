package com.example.skyqgo.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skyqgo.R
import com.example.skyqgo.util.getProgressDrawable
import com.example.skyqgo.util.loadImage
import com.example.skyqgo.model.Data
import kotlinx.android.synthetic.main.item_movies.view.*

class MoviesListAdapter (var movies:ArrayList<Data>) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {


    fun updateMovies(newMovies:List<Data>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = MovieViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movies,parent,false))


    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesListAdapter.MovieViewHolder, position: Int) {
       holder.bind(movies[position])
    }

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val movieImage = view.cv_iv_movie
        private val movieGenre = view.cv_tv_genre
        private val progressDrawable  = getProgressDrawable(view.context)

        fun bind(data: Data) {
            movieGenre.text = data.genre
            movieImage.loadImage(data.poster,progressDrawable)
        }
    }

}