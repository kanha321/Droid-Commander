package com.kanha.devicecontrol.composables

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.ActionSelectionActivity
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.models.Devices
import com.kanha.devicecontrol.operations.disconnectDevice
import com.kanha.devicecontrol.operations.getDeviceArrayList
import com.kanha.devicecontrol.operations.getRealSerialNumbers
import com.kanha.devicecontrol.operations.getSerialNumbers
import com.kanha.devicecontrol.operations.reconnectDevice
import com.kanha.devicecontrol.operations.wirelessConnection
import com.kanha.devicecontrol.ui.theme.Shapes
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import com.kanha.devicecontrol.util.SELECTED_DEVICE_MODEL

private const val TAG = "DevicesList"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesList(
    titleFontSize: TextUnit = 20.sp,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = 16.sp,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    shape: Shape = Shapes.medium,
    padding: Dp = 8.dp,
    isExpanded: Boolean = false,
    serialNumbers: ArrayList<String> = getRealSerialNumbers(),
    isIconGreen: Boolean = true,
    iconColor: Color = isIconGreen.let { if (it) Color.Green else Color.Red },
    context: Context,
) {
    var items = serialNumbers.map { Devices(it) }

    val selectedDevice = remember {
        mutableStateOf<Devices?>(null)
    }
    val itemState = rememberUpdatedState(items)

    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp),
        content = {
            items(itemState.value) { it ->
//                var showDialog by remember { mutableStateOf(false) }
//
//                if (showDialog) {
//
//                    OptionsDialog(options = options, onOptionClicked = {
//                        OPTION_SELECTED_INDEX = options.indexOf(it)
//                        showDialog = false
//                    }) {
//
//                    }
//                }
//                CallingDialog(
//                        onDismissRequest = { showDialog = false },
//                        onSubmit = { text ->
//                            showDialog = false
//                            makeACall(text.trimEverythingExceptPlusAndNumbers(), it.serialNumber)
//                        }
//                    )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    onClick = {
                        selectedDevice.value = it
                        SELECTED_DEVICE = it.serialNumber
                        SELECTED_DEVICE_MODEL = it.deviceModel
                        context.startActivity(Intent(context, ActionSelectionActivity::class.java))
//                        showDialog = true
                    },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(padding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(5f)
                        ) {
                            Text(
                                text = it.serialNumber,
                                fontSize = titleFontSize,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
                            )
                            Text(
                                text = it.deviceModel,
                                fontSize = descriptionFontSize,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                            )
                        }

                        Row() {
                            IconButton(
                                onClick = {

                                },
                                modifier = Modifier
                                    .offset(x = 4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_info_24),
                                    contentDescription = "info"
                                )
                            }
                            var isGreen by remember {
                                mutableStateOf(it.isIconGreen)
                            }
                            IconButton(
                                onClick = {
//                                    items = items.mapIndexed { index, item ->
//                                        if (index == items.indexOf(it)) {
//                                            item.copy(isIconGreen = !item.isIconGreen)
//                                        } else item
//                                    }
                                    if (it.isWireless) {
                                        if (isGreen) {
                                            Log.d(TAG, "DevicesList: disconnecting device")
                                            disconnectDevice(it.serialNumber)
                                            isGreen = false
                                            it.isIconGreen = false
                                        } else {
                                            Log.d(TAG, "DevicesList: reconnecting device")
                                            reconnectDevice(it.serialNumber)
                                            isGreen = true
                                            it.isIconGreen = true
                                        }
                                    } else {
                                        wirelessConnection(it.serialNumber)
                                    }
                                },
                                modifier = Modifier
                                    .padding(end = 12.dp)
                            ) {
                                if (it.isWireless) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_wifi_24),
                                        contentDescription = "wireless",
                                        tint = if (isGreen) Color.Green else Color.Red,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_cable_24),
                                        contentDescription = "wired",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun DeviceList2(
    titleFontSize: TextUnit = 20.sp,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = 16.sp,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    shape: Shape = Shapes.medium,
    padding: Dp = 8.dp,
    isExpanded: Boolean = false,
    serialNumbers: ArrayList<String> = getRealSerialNumbers()
) {

}

@Composable
fun LazyColumnWithRefresh(devices: List<Devices>) {
    // Create a state to track the list of devices
    val (deviceList, setDeviceList) = remember { mutableStateOf(devices) }

    // Display the lazy column with the devices' serial numbers and floating action button
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(deviceList) { device ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        )

                ) {
                    Text(device.serialNumber, modifier = Modifier.padding(16.dp))
                    IconButton(
                        onClick = {
                            disconnectDevice(device.serialNumber)
                            setDeviceList(getDeviceArrayList(getSerialNumbers()))
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                setDeviceList(getDeviceArrayList(getSerialNumbers()))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }
    }
}

//@Composable
//fun LazyColumnWithRefresh(devices: List<Devices>) {
//    // Create a state to track the list of devices
//    val (deviceList, setDeviceList) = remember { mutableStateOf(devices) }
//
//    // Display the lazy column with the device serial numbers and floating action button
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(modifier = Modifier.padding(16.dp)) {
//            items(deviceList) { device ->
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.onPrimaryContainer,
//                            shape = RoundedCornerShape(4.dp)
//                        )
//
//                ) {
//                    Text(device.serialNumber, modifier = Modifier.padding(16.dp))
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
//                    }
//                }
//            }
//        }
//        FloatingActionButton(
//            onClick = { setDeviceList(getDeviceArrayList(getSerialNumbers())) },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp)
//        ) {
//            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
//        }
//    }
//}
