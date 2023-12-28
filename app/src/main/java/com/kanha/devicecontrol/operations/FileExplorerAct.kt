package com.kanha.devicecontrol.operations

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanha.devicecontrol.customs.MyToast
import com.kanha.devicecontrol.models.FilesAndFolders
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_PULL_DESTINATION_PATH
import com.kanha.devicecontrol.util.DEFAULT_PUSH_DESTINATION_PATH
import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import com.kanha.devicecontrol.util.PULL_DESTINATION_PATH
import com.kanha.devicecontrol.util.PUSH_DESTINATION_PATH

private const val TAG = "FileExp"
var ext by mutableStateOf("all")

fun lsClient(path: String = CURRENT_FILE_EXPLORER_PATH, ext: String): ArrayList<FilesAndFolders>{
    val path = "\"${path.replace(" ", "\\ ")}\""
    Log.d(TAG, "ls: Selected device = $SELECTED_DEVICE")
    val command = "-s $SELECTED_DEVICE shell ls -l $path"
    Log.d(TAG, "ls: command = $command")
    val output = RunCommand.adb(command)
    return populateFileAndFolderArrayListWith(output.substring(output.indexOf("\n") + 1), ext)
}
fun lsHost(path: String = CURRENT_FILE_EXPLORER_PATH, ext: String): ArrayList<FilesAndFolders>{
    val path = "\"${path.replace(" ", "\\ ")}\""
    val command = "su -c ls -l $path"
    Log.d(TAG, "ls: command = $command")
    val output = RunCommand.shell(command)
    return populateFileAndFolderArrayListWith(output.substring(output.indexOf("\n") + 1), ext)
}

//fun lsAllFoldersAndOnlyFilesEndsWith(ext: String, path: String = CURRENT_FILE_EXPLORER_PATH){
//    val filesAndFolders = ""
//    val cmd1 = "-s $SELECTED_DEVICE shell ls -l -d $path*/"
//    val out1 = RunCommand.adb(cmd1)
//
//
//}

fun populateFileAndFolderArrayListWith(output: String, ext: String): ArrayList<FilesAndFolders>{
    val filesAndFolders = ArrayList<FilesAndFolders>()
    Log.d(TAG, "ls: output = $output")
    val lines = output.split("\n")
    for (line in lines){
        if (line.isNotEmpty()){
            val data = line.split("\\s+".toRegex())
            val type = data[0].substring(0, 1)
            val permissions = data[0].substring(1)
            val hardLinks = data[1]
            val owner = data[2]
            val group = data[3]
            val size = if (data[0].startsWith("d")) "Folder" else data[4]
            val date = data[5]
            val time = data[6]
            var name = ""
            for (i in 7 until data.size){
                name = "$name ${data[i]}"
            }
            name = name.trim()
            if (ext != "all" && !name.endsWith(ext) && !data[0].startsWith("d")) continue
            Log.d(TAG, "ls: type = $type\npermissions = $permissions\nhardLinks = $hardLinks\nowner = $owner\ngroup = $group\nsize = $size\ndate = $date\ntime = $time\nname = $name")
            filesAndFolders.add(FilesAndFolders(type, permissions, hardLinks, owner, group, size, date, time, name))
        }
    }
    return filesAndFolders
}

fun pull(path: String, context: Context): String {
    val model = getSingleDeviceModel(SELECTED_DEVICE)
    Log.d(TAG, "pull: model = $model")
    PULL_DESTINATION_PATH += "$model/"
    val mkdir = RunCommand.shell("mkdir -p $PULL_DESTINATION_PATH")
    Log.d(TAG, "pull: mkdir = $mkdir")
    val command = "-s $SELECTED_DEVICE pull \"$path\" $PULL_DESTINATION_PATH"
    Log.d(TAG, "pull: command = $command")
    MyToast.show(context, "Pulling file Be Patient!!\nThis may take a while...")
    val output = RunCommand.adb(command)
    Log.d(TAG, "pull: output = $output")
    PULL_DESTINATION_PATH = DEFAULT_PULL_DESTINATION_PATH
    return output
}

fun push(path: String, context: Context): String {
    val deviceName = "${RunCommand.shell(" getprop ro.product.brand", updateSessionLog = false).trim()}_${RunCommand.shell(" getprop ro.product.model", updateSessionLog = false).trim()}"
    Log.d(TAG, "push: deviceName = $deviceName/")
    PUSH_DESTINATION_PATH += "$deviceName/"
    val mkdir = RunCommand.adb("-s $SELECTED_DEVICE shell mkdir -p $PUSH_DESTINATION_PATH")
    Log.d(TAG, "push: mkdir = $mkdir")
    val command = "-s $SELECTED_DEVICE push \"$path\" $PUSH_DESTINATION_PATH"
    Log.d(TAG, "push: command = $command")
    MyToast.show(context, "Pushing file Be Patient!!\nThis may take a while...")
    val output = RunCommand.adb(command)
    Log.d(TAG, "push: output = $output")
    PUSH_DESTINATION_PATH = DEFAULT_PUSH_DESTINATION_PATH
    return output
}

fun installApk(path: String, context: Context): String {
    val command = "-s $SELECTED_DEVICE install -r \"$path\""
    Log.d(TAG, "installApk: command = $command")
    MyToast.show(context, "Installing Apk Be Patient!!\nThis may take a while...")
    val output = RunCommand.adb(command)
    Log.d(TAG, "installApk: output = $output")
    return output
}

