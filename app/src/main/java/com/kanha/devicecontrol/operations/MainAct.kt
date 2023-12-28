package com.kanha.devicecontrol.operations

import android.util.Log
import com.kanha.devicecontrol.models.Devices
import com.kanha.devicecontrol.refresh
import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.IS_DEVELOPER_MODE_ENABLED
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import java.util.Arrays

private const val TAG = "FunMainAct"

fun getRealSerialNumbers(): ArrayList<String> {
    val devices = ArrayList<String>()
    val output = RunCommand.adb("devices")
    // split the text by new line and store it in an array
    val lines = output.split("\n")
    for (line in lines) {
        if (line.endsWith("device")) {
            devices.add(line.substring(0, line.indexOf("device")).trim())
        }
    }
    return devices
}

val dummyDevices =
    ArrayList<String>(Arrays.asList("Dummy Device 1", "Dummy: Device 2", "Dummy Device 3"))

fun getDummySerialNumbers(): ArrayList<String> {
    return dummyDevices
}

fun getDeviceModels(devices: ArrayList<String>): ArrayList<String> {
    val deviceNames = ArrayList<String>()

    for (device in devices) {
        if (!device.startsWith("Dummy")) {
            val output = RunCommand.adb("-s $device shell getprop ro.product.model", false)
            deviceNames.add(output.trim())
        } else {
            deviceNames.add("Dummy Model ${devices.indexOf(device) + 1}")
        }
    }
    return deviceNames
}

fun getSingleDeviceModel(device: String): String {
    return if (device.startsWith("Dummy"))
        "Dummy Model ${device.substring(device.length - 1).toInt()}"
    else
        RunCommand.adb("-s $device shell getprop ro.product.model", false).trim()
}
fun getSpecificDeviceInfo(device: String, infoKey: String): String {
    return if (device.startsWith("Dummy"))
        "Dummy Model ${device.substring(device.length - 1).toInt()}"
    else
        RunCommand.adb("-s $device shell getprop ro.product.$infoKey", false, true).trim()
}

fun getAllDevicesAsText(devices: ArrayList<String>): Pair<Boolean, String> {
    var text = ""
    val isDeviceConnected: Boolean
    if (devices.isEmpty()) {
        isDeviceConnected = false
    } else {
        isDeviceConnected = true
        for (i in devices.indices) {
            text += "${i + 1}. ${devices[i]}"
            if (i != devices.size - 1) {
                text += "\n"
            }
        }
    }
    return Pair(isDeviceConnected, text)
}

fun wirelessConnection(device: String) {
    if (device.startsWith("Dummy")) {
        dummyDevices.add("${device.replace("Dummy", "Dummy:")} ${dummyDevices.size + 1}")
        Log.d(TAG, "wirelessConnection: Dummy device connected wirelessly")
        return
    }
    val command = "-s $device shell ip route"
    val outputIP = RunCommand.adb(command)
    val lines = outputIP.split(" ")
    val ip = lines[lines.size - 1].trim()

    val outputTCPIP = RunCommand.adb("-s $device tcpip 5555")
    Log.d(TAG, "wirelessConnection: $outputTCPIP")
    val outputConnect = RunCommand.adb("connect $ip:5555")
    Log.d(TAG, "wirelessConnection: connecting to $ip:5555")
    Log.d(TAG, "wirelessConnection: $outputConnect")
    refresh()
}

fun checkRootOnHost(): String {
    val output = RunCommand.shell("su -c id")
    return if (output.contains("Permission denied"))
        "Unable to get root access"
    else
        "Root access granted"
}

fun isDeviceConnectionWireless(device: String): Boolean {
    val isWireless = device.contains(":")
    Log.d(TAG, "isDeviceConnectionWireless: $isWireless")
    return isWireless
}

fun disconnectDevice(device: String): Boolean {
    return if (device.startsWith("Dummy")) {
        dummyDevices.remove(device)
        true
    } else {
        val output = RunCommand.adb("disconnect $device")
        Log.d(TAG, "disconnectDevice: $output")
        output.contains("disconnected") && output.contains(":")
    }
}

fun reconnectDevice(device: String): Boolean {
    return if (device.startsWith("Dummy")) {
        dummyDevices.add(device)
        refresh()
        true
    } else {
        val output = RunCommand.adb("connect $device")
        Log.d(TAG, "reconnectDevice: $output")
        refresh()
        output.contains("connected") && output.contains(":")
    }
}

fun getDeviceArrayList(serialNumbers: ArrayList<String>): ArrayList<Devices> {
    val devices = ArrayList<Devices>()
    for (i in serialNumbers.indices) {
        devices.add(Devices(serialNumbers[i]))
    }
    return devices
}

fun getDeviceArrayListDummy(serialNumbers: ArrayList<String> = ArrayList()): ArrayList<Devices> {
    val devices = ArrayList<Devices>()
    for (i in 0..5) {
        devices.add(Devices("Dummy Device ${i + 1}", true, "Dummy Model ${i + 1}"))
    }
    return devices
}

fun getSerialNumbers(): ArrayList<String> {
    return if (IS_DEVELOPER_MODE_ENABLED) {
        getDummySerialNumbers()
    } else {
        getRealSerialNumbers()
    }
}

fun refreshDeviceList(setDeviceList: (List<Devices>) -> Unit) {
    setDeviceList(getDeviceArrayList(getSerialNumbers()))
}

fun getDeviceAndModel(serialNumber: String = SELECTED_DEVICE): String{

    if (!IS_DEVELOPER_MODE_ENABLED) {

        return "${
            RunCommand.adb(
                command = "-s $serialNumber shell getprop ro.product.brand",
                updateSessionLog = false,
                returnOnlyOutput = true
            )
        } ${
            RunCommand.adb(
                command = "-s $serialNumber shell getprop ro.product.model",
                updateSessionLog = false,
                returnOnlyOutput = true
            )
        }".ifBlank { "Error" }
    } else {
        return "X ${
            RunCommand.shell(
                command = "getprop ro.product.brand",
                updateSessionLog = false,
            )
        } ${
            RunCommand.shell(
                command = "getprop ro.product.model",
                updateSessionLog = false,
            )
        }".ifBlank { "Error" }
    }
}

//fun isDeviceConnected(serialNumbers: ArrayList<String> = getSerialNumbers()): Boolean {
//    return serialNumbers.isNotEmpty()
//}
