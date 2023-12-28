package com.kanha.devicecontrol.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionsDialog(
    options: List<String>,
    onOptionClicked: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select an Option") },
        text = {
            LazyColumn {
                items(options) { option ->
                    Text(
                        text = option,
                        modifier = Modifier
                            .clickable { onOptionClicked(option) }
                            .padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        }
    )
}
