package com.kanha.devicecontrol.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun <T : Any> reloadableComposable(composable: @Composable (MutableState<T>) -> Unit): @Composable () -> Unit {
    val state = mutableStateOf<T?>(null)
    return {
        composable(state as MutableState<T>)
        Button(onClick = {
            state.value = state.value
        }) {
            Text("Reload")
        }
    }
}
