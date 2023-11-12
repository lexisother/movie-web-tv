package dev.alyxia.movietv.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.moviebase.tmdb.model.TmdbMoviePageResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alyxia.movietv.service.MovieService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieService: MovieService
) : ViewModel() {
    private val internalSearchState = MutableSharedFlow<SearchState>()

    fun query(query: String) {
        viewModelScope.launch { search(query) }
    }

    private suspend fun search(query: String) {
        internalSearchState.emit(SearchState.Searching)
        val result = movieService.searchMovies(query)
        internalSearchState.emit(SearchState.Done(result))
    }

    val searchState = internalSearchState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchState.Done(TmdbMoviePageResult(0, listOf(), 0, 0))
    )
}

sealed interface SearchState {
    data object Searching : SearchState
    data class Done(val movieList: TmdbMoviePageResult) : SearchState
}