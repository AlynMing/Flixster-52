package com.moise.flixster.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.moise.flixster.model.Movie
import com.moise.flixster.adapter.MovieAdapter
import com.moise.flixster.databinding.ActivityMovieBinding
import okhttp3.Headers
import org.json.JSONException


class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    companion object  {
        const val TAG = "MovieActivity"
        private const val NOW_PLAYING_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
    }
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovie: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvMovie = binding.rvMovieList

        val movieAdapter = MovieAdapter(this, movies)
        rvMovie.adapter = movieAdapter
        rvMovie.layoutManager = LinearLayoutManager(this)
        Log.d(TAG, "onCreate: $movies")
        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyItemRangeInserted(0, movies.size - 1)
                } catch (e: JSONException) {
                }
            }
        })
    }


}