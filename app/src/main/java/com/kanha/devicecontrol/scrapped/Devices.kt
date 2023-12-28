package com.kanha.devicecontrol.scrapped

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.operations.getDeviceModels
import com.kanha.devicecontrol.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceList(
    serialNumbers: ArrayList<String>,
    serialNumberFontSize: TextUnit = 20.sp,
    serialNumberFontWeight: FontWeight = FontWeight.Bold,
    model: ArrayList<String> = getDeviceModels(serialNumbers),
    modelFontSize: TextUnit = 16.sp,
    modelFontWeight: FontWeight = FontWeight.Normal,
    shape: Shape = Shapes.medium,
    padding: Dp = 18.dp,

    ) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            shape = shape,
            onClick = {

            }
        ) {
            Text(
                text = model[0],
                fontSize = modelFontSize,
                fontWeight = modelFontWeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            )
        }
    }
}

//@Composable
//@Preview
//fun DeviceListPreview() {
//    DeviceList(serialNumbers = getSerialNumbers())
//}