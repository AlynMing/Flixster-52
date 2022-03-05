package com.moise.flixster.ui

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.moise.flixster.R
import com.moise.flixster.constants.SELECTED_MOVIE
import com.moise.flixster.databinding.ActivityDetailBinding
import com.moise.flixster.model.Movie
import okhttp3.Headers

class DetailActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        private const val YOUTUBE_API_KEY = "AIzaSyAW_BBgfpaVWT_LQQKBUS8lqPb_G28v3SU"
        private const val THRILLER_URL =  "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        const val TAG = "DetailActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(SELECTED_MOVIE) as Movie
        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.rbVoteAverage.rating = movie.voteAverage.toFloat()
        val client = AsyncHttpClient()
        client.get(THRILLER_URL.format(movie.movieId), object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d(TAG, "onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: $statusCode")
                val thrillerArray = json.jsonObject.getJSONArray("results")
                if(thrillerArray.length() == 0) {
                    Log.d(TAG, "onSuccess: No movie thriller found")
                    return
                }
                else{
                    val movieThrillerJson = thrillerArray.getJSONObject(0)
                    val thrillerKey = movieThrillerJson.getString("key")
                    if (movie.voteAverage > 5)
                        initializeYoutube(thrillerKey, true)

                    else
                        initializeYoutube(thrillerKey, false)

                }

            }

        })
    }

    private fun initializeYoutube(thrillerKey: String, isPopular: Boolean) {
        binding.player.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer,
                p2: Boolean
            ) {
                Log.d(TAG, "onInitializationSuccess: called")
                if (isPopular)
                    player.loadVideo(thrillerKey)
                else
                    player.cueVideo(thrillerKey)
            }

            override fun onInitializationFailure(
                privider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                Log.d(TAG, "onInitializationFailure: called")
            }


        })
    }
}