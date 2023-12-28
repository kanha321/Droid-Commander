package com.kanha.devicecontrol.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.models.DropDownItem

@Composable
fun LongClickMenu(
    name: String,
//    icon: Int,  // TODO: Add icon
    dropDownItems: ArrayList<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick : (DropDownItem) -> Unit
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
        modifier = modifier.onSizeChanged {
            itemHeight = with(density) { it.height.toDp() }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                    )
                }
                .padding(16.dp)
        ) {
            Text(text = name)
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