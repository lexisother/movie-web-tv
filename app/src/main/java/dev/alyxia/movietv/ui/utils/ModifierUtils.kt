package dev.alyxia.movietv.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester

data class FocusRequesterModifiers(
    val parentModifier: Modifier,
    val childModifier: Modifier
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun createInitialFocusRestorerModifiers(): FocusRequesterModifiers {
    val focusRequester = remember { FocusRequester() }
    val childFocusRequester = remember { FocusRequester() }

    val parentModifier = Modifier
        .focusRequester(focusRequester)
        .focusProperties {
            exit = {
                focusRequester.saveFocusedChild()
                FocusRequester.Default
            }
            enter = {
                if (focusRequester.restoreFocusedChild()) FocusRequester.Cancel
                else childFocusRequester
            }
        }

    val childModifier = Modifier.focusRequester(childFocusRequester)

    return FocusRequesterModifiers(parentModifier, childModifier)
}