package com.kanha.devicecontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times


@Composable
fun ScrollableText(
    output: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .border(2.dp, Color.Gray)
            .heightIn(max = 15 * 20.dp) // 20dp is the default line height
            .background(Color.Black)
            .padding(16.dp)
            .then(modifier),
        reverseLayout = true // Scroll to bottom by default
    ) {
        item {
            Text(
                text = output,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ScrollableText(
//    output: String,
//    modifier: Modifier = Modifier,
//    onCommandEntered: (command: String) -> Unit
//) {
//    val scrollState = rememberScrollState()
//
//    Box(modifier = modifier) {
//        Column(
//            modifier = Modifier.verticalScroll(scrollState)
//        ) {
//            Text(output)
//        }
//        OutlinedTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    top = 0.dp,
//                    bottom = 20.dp,
//                    start = 20.dp,
//                    end = 20.dp
//                ),
//            value = "",
//            onValueChange = {},
//            singleLine = true,
//            label = { Text("Run Shell Commands") },
//            readOnly = false,
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Done,
//                keyboardType = KeyboardType.Text
//            ),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    onCommandEntered("")
//                }
//            )
//        )
//    }
//}

