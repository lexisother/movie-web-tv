package dev.alyxia.movietv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.Text
import dev.alyxia.movietv.ui.theme.Typography

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Home(navController: NavController) {
    var text by remember { mutableStateOf("") }
    val tfFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val tfInteractionSource = remember { MutableInteractionSource() }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(top = 120.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp),
            fontSize = 25.sp,
            text = "What do you want to watch?"
        )
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
                    LocalContentColor.current,
                    LocalContentColor.current
                )
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    println(text)
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
        Button(onClick = { /*TODO*/ }) {
            Text("gi")
        }
    }
}