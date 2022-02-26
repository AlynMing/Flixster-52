package com.moise.flixster

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val context: Context, private val movies: List<Movie>):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.ivPoster)
        val movieTitle: TextView = view.findViewById(R.id.tvTitle)
        val movieOverview: TextView = view.findViewById(R.id.tvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.movieTitle.text = currentMovie.title
        holder.movieOverview.text = currentMovie.overview
        // Binding the image
        val orientation = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide
                .with(context)
                .load(currentMovie.posterImageUrl)
                .placeholder(R.drawable.poster_placeholder)
                .into(holder.moviePoster)
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Glide
                .with(context)
                .load(currentMovie.posterBackdropUrl)
                .placeholder(R.drawable.poster_placeholder)
                .into(holder.moviePoster)
        }
    }

    override fun getItemCount() = movies.size
}
