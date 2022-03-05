package com.moise.flixster.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
@Parcelize
data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    private val backdrop_path: String


    ) : Parcelable {
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    @IgnoredOnParcel
    val posterBackdropUrl = "https://image.tmdb.org/t/p/w300/$backdrop_path"
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie>{
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()){
                val movieJson =  movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie (
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("backdrop_path")
                            )
                )
            }

            return movies

        }
    }
}