package com.kanha.devicecontrol

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kanha.devicecontrol.composables.ExpandableCard
import com.kanha.devicecontrol.composables.Toolbar
import com.kanha.devicecontrol.composables.UsbOtgEvent
import com.kanha.devicecontrol.models.Devices
import com.kanha.devicecontrol.operations.getSerialNumbers
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme
import com.kanha.devicecontrol.util.IS_DEVELOPER_MODE_ENABLED
import com.kanha.devicecontrol.util.IS_DEVICE_CONNECTED

private const val TAG = "MainActivity"


var serialNumbers by mutableStateOf(ArrayList<String>())
private var devices = ArrayList<Devices>()

var showCallDialog by mutableStateOf(false)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        refresh()

//        if(!fileExists(this)){
//            extractAssets(this)
//        } else {
//            Log.d(TAG, "onCreate: file exists")
//        }

        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UsbOtgEvent(
                        onDeviceAttached = {
                            IS_DEVELOPER_MODE_ENABLED = false
                            refresh()
                        }, onDeviceDetached = {
                            refresh()
                        }
                    )
                    Column {
                        Toolbar(context = this@MainActivity, onRefresh = { refresh() })
                        ExpandableCard(
                            title = if (IS_DEVELOPER_MODE_ENABLED)
                                "Connected Dummy Devices"
                            else if (IS_DEVICE_CONNECTED && !IS_DEVELOPER_MODE_ENABLED)
                                "Connected Devices"
                            else
                                "No Device Connected",
                            isExpanded = true,
                            serialNumbers = serialNumbers,
                            context = this@MainActivity,
                        )
//                        Button (
//                            onClick = {
//                                      startActivity(Intent(this@MainActivity, TestActivity2::class.java))
//                            },
//                            modifier = Modifier
//                                .padding(top = 116.dp, start = 24.dp, end = 16.dp)
//                        ) {
//                            Text(text = "Button")
//                        }
//                        Text(text = OPTION_SELECTED_INDEX.toString().clearString())
//                        if (OPTION_SELECTED_INDEX == 0) {
//                            if(showCallDialog){
//                                CallingDialog(
//                                    onDismissRequest = { showCallDialog = false },
//                                    onSubmit = { text ->
//                                        showCallDialog = false
//                                        makeACall(
//                                            text.trimEverythingExceptPlusAndNumbers(),
//                                            SELECTED_DEVICE
//                                        )
//                                    }
//                                )
//                            }
//                            OPTION_SELECTED_INDEX = -1
//                        }
//                        else if (OPTION_SELECTED_INDEX == 1) {
//                            startActivity(Intent(this@MainActivity, FileExplorerActivity::class.java))
//                            OPTION_SELECTED_INDEX = -1
//                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
}

fun refresh() {
    devices.clear()
    serialNumbers = getSerialNumbers()
    Log.d(TAG, "refresh: serialNumbers = $serialNumbers")
    for (i in serialNumbers.indices) {
        devices.add(Devices(serialNumbers[i]))
    }

    IS_DEVICE_CONNECTED = devices.size > 0

    for (i in devices.indices) {
        Log.d(TAG, "refresh: device ${i + 1} model = ${devices[i].deviceModel}")
        Log.d(TAG, "refresh: device ${i + 1} serial = ${devices[i].serialNumber}")
        Log.d(TAG, "refresh: device ${i + 1} isWireless = ${devices[i].isWireless}")
    }
    Log.d(TAG, "refresh: device size = ${devices.size}")
}