package com.kanha.devicecontrol.models

import androidx.compose.ui.graphics.Color
import com.kanha.devicecontrol.R.drawable
import com.kanha.devicecontrol.operations.getSingleDeviceModel
import com.kanha.devicecontrol.operations.isDeviceConnectionWireless

data class Devices(
    val serialNumber: String,
    var isWireless: Boolean = isDeviceConnectionWireless(serialNumber),
    var deviceModel: String = getSingleDeviceModel(serialNumber),
    var icon: Int =
        if (isWireless) drawable.round_wifi_24
        else drawable.outline_cable_24,
    var isIconGreen: Boolean = true,
    var iconColor: Color = if (isIconGreen) Color.Green else Color.Red
)
