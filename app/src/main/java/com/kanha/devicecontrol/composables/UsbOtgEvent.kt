package com.kanha.devicecontrol.composables

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private const val TAG = "UsbOtgEvent"

@Composable
fun UsbOtgEvent(onDeviceAttached: () -> Unit, onDeviceDetached: () -> Unit) {
    val context = LocalContext.current
    val usbDeviceAttached = remember { mutableStateOf(false) }

    val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    usbDeviceAttached.value = true
                    onDeviceAttached()
                    Log.d(TAG, "onReceive: device attached")
                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    usbDeviceAttached.value = false
                    onDeviceDetached()
                    Log.d(TAG, "onReceive: device detached")
                }
            }
        }
    }

    DisposableEffect(usbReceiver) {
        context.registerReceiver(
            usbReceiver,
            IntentFilter().apply {
                addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
                addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
            }
        )

        onDispose {
            context.unregisterReceiver(usbReceiver)
        }
    }
}
