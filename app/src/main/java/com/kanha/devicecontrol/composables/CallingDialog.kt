package com.kanha.devicecontrol.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.kanha.devicecontrol.operations.getSpecificDeviceInfo
import com.kanha.devicecontrol.util.COUNTRY_CODE
import com.kanha.devicecontrol.util.SELECTED_DEVICE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallingDialog(
    onDismissRequest: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var text by remember { mutableStateOf("$COUNTRY_CODE ") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Call from ${getSpecificDeviceInfo(SELECTED_DEVICE, "device").ifBlank { "<Error>" }}") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                keyboardActions = KeyboardActions(onDone = { onSubmit(text) })
            )
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(text) }
            ) {
                Text("Call")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        }
    )
}

