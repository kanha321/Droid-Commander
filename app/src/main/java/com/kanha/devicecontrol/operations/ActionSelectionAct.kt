package com.kanha.devicecontrol.operations

import android.app.Activity
import android.content.Context
import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import com.kanha.devicecontrol.operations.PowerState.*
import com.kanha.devicecontrol.showImageDialog
import com.kanha.devicecontrol.util.DEFAULT_PULL_DESTINATION_PATH
import com.kanha.devicecontrol.util.PULL_DESTINATION_PATH

fun powerControl(state: PowerState, context: Context){
    when (state) {
        REBOOT -> RunCommand.adb("-s $SELECTED_DEVICE shell reboot")
        RECOVERY -> RunCommand.adb("-s $SELECTED_DEVICE shell reboot recovery")
        BOOTLOADER -> RunCommand.adb("-s $SELECTED_DEVICE shell reboot bootloader")
        SHUTDOWN -> RunCommand.adb("-s $SELECTED_DEVICE shell reboot -p")
        LOCK -> RunCommand.adb("-s $SELECTED_DEVICE shell input keyevent 26")
        SCREENSHOT -> RunCommand.adb("-s $SELECTED_DEVICE shell screencap -p /sdcard/screenshot.png")
    }
    if (state == REBOOT || state == RECOVERY || state == BOOTLOADER || state == SHUTDOWN) {
        (context as Activity).finish()
    } else if (state == SCREENSHOT) {
        // pull the screenshot to cache directory
        RunCommand.adb("-s $SELECTED_DEVICE pull /sdcard/screenshot.png ${context.cacheDir.absolutePath}")
        showImageDialog = true
    }
}

enum class PowerState {
    REBOOT, RECOVERY, BOOTLOADER, SHUTDOWN, LOCK, SCREENSHOT
}