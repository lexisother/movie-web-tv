package dev.alyxia.movietv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import android.view.KeyEvent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import app.moviebase.tmdb.model.TmdbMoviePageResult
import dev.alyxia.movietv.ui.theme.Typography

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    // I know, bit of a weird structure here having the "results" component contain the search bar.
    // Might swap that out at some point.
    when (val s = searchState) {
        is SearchState.Searching -> {
            Text("Searching...")
        }

        is SearchState.Done -> {
            val movieList = s.movieList
            Results(movieList, viewModel::query)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Results(
    movieList: TmdbMoviePageResult,
    searchMovies: (query: String) -> Unit,
    tvLazyColumnState: TvLazyListState = rememberTvLazyListState()
) {
    var text by remember { mutableStateOf("") }
    val tfFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val tfInteractionSource = remember { MutableInteractionSource() }

    TvLazyColumn(state = tvLazyColumnState) {
        item {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 8.dp
                    )
                    .focusRequester(tfFocusRequester)
                    .onKeyEvent {
                        if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                            when (it.nativeKeyEvent.keyCode) {
                                KeyEvent.KEYCODE_DPAD_DOWN -> {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }

                                KeyEvent.KEYCODE_DPAD_UP -> {
                                    focusManager.moveFocus(FocusDirection.Up)
                                }

                                KeyEvent.KEYCODE_BACK -> {
                                    focusManager.moveFocus(FocusDirection.Exit)
                                }
                            }
                        }
                        true
                    },
                cursorBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black
                    )
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchMovies(text)
                    }
                ),
                maxLines = 1,
                interactionSource = tfInteractionSource
            ) {
                Box(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(vertical = 16.dp)
                        .padding(start = 20.dp)
                ) {
                    it()
                    if (text.isEmpty()) {
                        Text(
                            modifier = Modifier.graphicsLayer { alpha = 0.6f },
                            text = "Movie name...",
                            style = Typography.titleSmall,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        item {
            Text(movieList.toString())
        }
    }

//    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
//        Button(onClick = {
////            viewModel.query(text)
//        }) {
//            Text("what")
//        }
//        Text(
//            modifier = Modifier
//                .padding(top = 120.dp)
//                .align(Alignment.CenterHorizontally)
//                .padding(bottom = 30.dp),
//            fontSize = 25.sp,
//            text = "What do you want to watch?"
//        )
//        BasicTextField(
//            value = text,
//            onValueChange = { text = it },
//            modifier = Modifier
//                .fillMaxWidth(0.5f)
//                .padding(
//                    vertical = 4.dp,
//                    horizontal = 8.dp
//                )
//                .focusRequester(tfFocusRequester)
//                .onKeyEvent {
//                    if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
//                        when (it.nativeKeyEvent.keyCode) {
//                            KeyEvent.KEYCODE_DPAD_DOWN -> {
//                                focusManager.moveFocus(FocusDirection.Down)
//                            }
//
//                            KeyEvent.KEYCODE_DPAD_UP -> {
//                                focusManager.moveFocus(FocusDirection.Up)
//                            }
//
//                            KeyEvent.KEYCODE_BACK -> {
//                                focusManager.moveFocus(FocusDirection.Exit)
//                            }
//                        }
//                    }
//                    true
//                },
//            cursorBrush = Brush.verticalGradient(
//                colors = listOf(
//                    Color.Black,
//                    Color.Black
//                )
//            ),
//            keyboardOptions = KeyboardOptions(
//                autoCorrect = false,
//                imeAction = ImeAction.Search
//            ),
//            keyboardActions = KeyboardActions(
//                onSearch = {
//                    searchMovies(text)
//                }
//            ),
//            maxLines = 1,
//            interactionSource = tfInteractionSource
//        ) {
//            Box(
//                Modifier
//                    .background(Color.LightGray, RoundedCornerShape(percent = 30))
//                    .padding(vertical = 16.dp)
//                    .padding(start = 20.dp)
//            ) {
//                it()
//                if (text.isEmpty()) {
//                    Text(
//                        modifier = Modifier.graphicsLayer { alpha = 0.6f },
//                        text = "Movie name...",
//                        style = Typography.titleSmall,
//                        color = Color.Black
//                    )
//                }
//            }
//        }
//    }
}