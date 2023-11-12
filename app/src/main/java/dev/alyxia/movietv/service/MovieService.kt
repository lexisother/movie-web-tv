package dev.alyxia.movietv.service

import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.model.TmdbMoviePageResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService @Inject constructor(
    private val tmdb: Tmdb3
) {
    suspend fun searchMovies(query: String): TmdbMoviePageResult {
        return tmdb.search.findMovies(query, 1)
    }
}