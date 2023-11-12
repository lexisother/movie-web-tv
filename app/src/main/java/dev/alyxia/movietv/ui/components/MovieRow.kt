package dev.alyxia.movietv.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import app.moviebase.tmdb.model.TmdbMovie
import app.moviebase.tmdb.model.TmdbMoviePageResult
import dev.alyxia.movietv.rememberChildPadding
import dev.alyxia.movietv.ui.utils.createInitialFocusRestorerModifiers

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    startPadding: Dp = rememberChildPadding().start,
    endPadding: Dp = rememberChildPadding().end,
    title: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    movies: List<TmdbMovie>
) {
    Column(
        modifier = modifier.focusGroup()
    ) {
        title?.let {
            Text(
                text = it,
                style = titleStyle,
                modifier = Modifier
                    .alpha(1f)
                    .padding(start = startPadding)
                    .padding(vertical = 16.dp)
            )
        }

        AnimatedContent(
            targetState = movies,
            label = ""
        ) {
            val focusRestorerModifiers = createInitialFocusRestorerModifiers()

            TvLazyRow(
                modifier = Modifier.then(focusRestorerModifiers.parentModifier),
                pivotOffsets = PivotOffsets(parentFraction = 0.07f),
                contentPadding = PaddingValues(
                    start = startPadding,
                    end = endPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(it, key = { _, movie -> movie.id }) { index, movie ->
                    Text(text = movie.title)
                }
            }
        }
    }
}