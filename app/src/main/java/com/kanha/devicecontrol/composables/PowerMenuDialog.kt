package com.kanha.devicecontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanha.devicecontrol.models.PowerControlItem
import kotlin.math.ceil

@Composable
fun PowerMenuDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    powerControlItems: ArrayList<PowerControlItem>,
    gridColumn: Int = if (powerControlItems.size % 2 == 0) powerControlItems.size / 2 - 1 else powerControlItems.size / 2,
    gridRow: Int = 2
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(36.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .sizeIn(minWidth = 440.dp, minHeight = 400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(28.dp)
                ) {
                    for (i in 0..gridColumn) {
                        Row{
                            for (j in 0..(
                                    if (i == gridColumn)
                                        if (powerControlItems.size % 2 == 0) 1
                                        else 0
                                    else 1
                                    )) {
                                val index = i * 2 + j
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(color = MaterialTheme.colorScheme.surface)
                                            .clip(
                                                RoundedCornerShape(100.dp)
                                            ),
                                    ) {
                                        NoRippleIconButton(
                                            modifier = Modifier.size(100.dp),
                                            onClick = {
                                                powerControlItems[index].onClick()
                                            },

                                        ) {
                                            Icon(
                                                painter = painterResource(id = powerControlItems[index].icon),
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = powerControlItems[index].name,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(16.dp))
                    Text(
                        text = "Note: Powering off the device will disconnect it from the app.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            }
        }
    }
}