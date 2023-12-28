package com.kanha.devicecontrol.backups

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.customs.MyToast
import com.kanha.devicecontrol.models.DropDownItem
import com.kanha.devicecontrol.operations.pull
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.SELECTED_FILE
import com.kanha.devicecontrol.util.SELECTED_FILE_WITH_PATH

private const val TAG = "FileExplorerCard"

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
    dropDownItems: ArrayList<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Card(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .fillMaxWidth()
            .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp),
        onClick = {
            if (type == "d") {
                CURRENT_FILE_EXPLORER_PATH = "$CURRENT_FILE_EXPLORER_PATH$name"
                CURRENT_FILE_EXPLORER_PATH += "/"
            } else {
                SELECTED_FILE = name
                pull("$CURRENT_FILE_EXPLORER_PATH$name", context)
                Log.d(TAG, "FileExplorerCard: $SELECTED_FILE_WITH_PATH")
//                MyToast.show(context, "File Downloaded $selectedFileWithPath")
            }
        },
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                            SELECTED_FILE = name
                        },
                        onTap = {
                            if (type == "d") {
                                CURRENT_FILE_EXPLORER_PATH = "$CURRENT_FILE_EXPLORER_PATH$name"
                                CURRENT_FILE_EXPLORER_PATH += "/"
                            } else {
                                SELECTED_FILE = name
//                                pull("$currentFileExplorerPath$name", context)
                                Log.d(TAG, "FileExplorerCard: $SELECTED_FILE_WITH_PATH")
                                MyToast.show(context, "File Downloaded $SELECTED_FILE_WITH_PATH")
                            }
                        }
                    )
                },
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
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(y = pressOffset.y - itemHeight)
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemClick(item)
                        isContextMenuVisible = false
                    },
                    text = {
                        Text(text = item.text)
                    }
                )
            }
        }
    }
}