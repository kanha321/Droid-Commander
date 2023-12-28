package com.kanha.devicecontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
@Suppress("unused")
fun ConnectedDevices(deviceList: ArrayList<String>, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 70.dp)
            .padding(16.dp),
//        contentAlignment = Alignment.Center

    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = deviceList[0],
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
            )
        }
    }
}


@Composable
fun RectangularButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}
