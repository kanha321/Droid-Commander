package com.kanha.devicecontrol.operations

import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.SELECTED_DEVICE

fun getInstalledPackages(): ArrayList<String> {
    val command = "-s $SELECTED_DEVICE shell pm list packages"
    val output = RunCommand.adb(command)
    val packages = arrayListOf<String>()
    output.split("\n").forEach {
        if (it.isNotEmpty()) {
            packages.add(it.substring(it.indexOf(":") + 1))
        }
    }
    return packages
}

fun launchApp(packageName: String) {
    val command = "-s $SELECTED_DEVICE shell monkey -p $packageName -c android.intent.category.LAUNCHER 1"
    RunCommand.adb(command)
}