package com.kanha.devicecontrol.composables

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.operations.getRealSerialNumbers
import com.kanha.devicecontrol.ui.theme.Shapes

private const val TAG = "ExpandableCard"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = 20.sp,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = 16.sp,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    shape: Shape = Shapes.medium,
    padding: Dp = 8.dp,
    isExpanded: Boolean = false,
    serialNumbers: ArrayList<String> = getRealSerialNumbers(),
    context: Context,
) {
    var expandedState by remember { mutableStateOf(isExpanded) }
    var isConnectionWireless = remember { mutableStateListOf<Int>()}

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding * 3),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }

    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(6f),
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.5f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            AnimatedVisibility(visible = expandedState) {
                Column {
                    DevicesList(serialNumbers = serialNumbers, context = context)
                }
            }
        }
    }
}