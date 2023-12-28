package com.kanha.devicecontrol.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.operations.installApk
import com.kanha.devicecontrol.operations.pull
import com.kanha.devicecontrol.operations.push
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.OPERATION
import com.kanha.devicecontrol.util.Operation.*
import com.kanha.devicecontrol.util.SELECTED_FILE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerCard(
    type: String,
    permissions: String,
    hardLinks: String,
    owner: String,
    group: String,
    size: String,
    date: String,
    time: String,
    name: String,
    context: Context,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp),
        onClick = {
            if (type == "d") {
                if (CURRENT_FILE_EXPLORER_PATH == "/storage/" && name == "emulated") {
                    CURRENT_FILE_EXPLORER_PATH = "$CURRENT_FILE_EXPLORER_PATH$name/0/"
                } else {
                    CURRENT_FILE_EXPLORER_PATH = "$CURRENT_FILE_EXPLORER_PATH$name"
                    CURRENT_FILE_EXPLORER_PATH += "/"
                }
            }
            else {
                SELECTED_FILE = name
                if (OPERATION == PULL) {
                    pull("$CURRENT_FILE_EXPLORER_PATH$name", context)
                } else if (OPERATION == PUSH) {
                    push("$CURRENT_FILE_EXPLORER_PATH$name", context)
                } else if (OPERATION == INSTALL) {
                    installApk("$CURRENT_FILE_EXPLORER_PATH$name", context)
                }
            }
        },
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = if (type == "d") R.drawable.baseline_folder_24 else R.drawable.outline_file_24),
                contentDescription = type, // Provide a meaningful description if needed
                modifier = Modifier.size(24.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$date $time",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
    }
}