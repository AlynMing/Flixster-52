package com.moise.flixster.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.moise.flixster.R
import com.moise.flixster.constants.SELECTED_MOVIE
import com.moise.flixster.model.Movie
import com.moise.flixster.ui.DetailActivity
import com.moise.flixster.ui.MovieActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class MovieAdapter(private val context: Context, private val movies: List<Movie>):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    companion object {
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private val moviePoster: ImageView = view.findViewById(R.id.ivPoster)
        private val movieTitle: TextView = view.findViewById(R.id.tvTitle)
        private val movieOverview: TextView = view.findViewById(R.id.tvOverview)

        init {
            view.setOnClickListener(this) // Overriding onClick() would work the same way

        }
        fun bind(currentMovie: Movie) {
            movieTitle.text = currentMovie.title
            movieOverview.text = currentMovie.overview
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide
                    .with(context)
                    .load(currentMovie.posterImageUrl)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(moviePoster)
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide
                    .with(context)
                    .load(currentMovie.posterBackdropUrl)
//                    .transform(RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.poster_placeholder)
                    .into(moviePoster)
            }
        }
        //Overriding the onClick method does not register the view for click events
        override fun onClick(v: View?) {
            val movie = movies[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SELECTED_MOVIE, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as MovieActivity, (movieOverview as View?)!!, "overview")
            context.startActivity(intent, options.toBundle())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount() = movies.size
}
