package com.kanha.devicecontrol.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.util.Log
import com.kanha.devicecontrol.customs.MyToast
import com.kanha.devicecontrol.models.Logs
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private const val TAG = "Operations"

@Throws(IOException::class)
fun unzip(zipFile: File?, targetDirectory: File?) {
    val zis = ZipInputStream(
        BufferedInputStream(FileInputStream(zipFile))
    )
    try {
        var ze: ZipEntry
        var count: Int
        val buffer = ByteArray(8192)
        while (zis.nextEntry.also { ze = it } != null) {
            val file = File(targetDirectory, ze.name)
            val dir = if (ze.isDirectory) file else file.parentFile
            if (!dir.isDirectory && !dir.mkdirs()) throw FileNotFoundException(
                "Failed to ensure directory: " + dir.absolutePath
            )
            if (ze.isDirectory) continue
            val fout = FileOutputStream(file)
            try {
                while (zis.read(buffer).also { count = it } != -1) fout.write(buffer, 0, count)
            } finally {
                fout.close()
            }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
        }
    } finally {
        zis.close()
    }
}

@Throws(IOException::class)
fun copyFile(`in`: InputStream, out: OutputStream) {
    val buffer = ByteArray(1024)
    var read: Int
    while (`in`.read(buffer).also { read = it } != -1) {
        out.write(buffer, 0, read)
    }
}

private fun copyAsset(context: Context, filename: String = "tools.zip") {

    val dirPath = context.filesDir.absolutePath + File.separator + "assets"

    val dir = File(dirPath)
    if (!dir.exists()) {
        dir.mkdir()
    }
    val assetManager: AssetManager = context.assets
    var `in`: InputStream? = null
    var out: OutputStream? = null
    try {
        `in` = assetManager.open(filename)
        val outFile = File(dirPath, filename)
        out = FileOutputStream(outFile)
        copyFile(`in`, out)
        MyToast.show(context, "Copied $filename to $dirPath")
    } catch (e: IOException) {
        MyToast.show(context, "Failed to copy asset file: $filename")
    } finally {
        if (`in` != null) {
            try {
                `in`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (out != null) {
            try {
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

// check whether the extracted files of tools.zip exists or not it contains a file named adb

fun fileExists(context: Context): Boolean {
    val dirPath = context.filesDir.absolutePath + File.separator + "assets"
    val file = File(dirPath, "adb")
    return file.exists()
}

fun extractAssets(context: Context) {
    if (!fileExists(context)) {
        copyAsset(context)
        val dirPath = context.filesDir.absolutePath + File.separator + "assets"
        val zipFile = File(dirPath, "tools.zip")
        val targetDirectory = File(dirPath)
        unzip(zipFile, targetDirectory)
    }
}

@SuppressLint("SimpleDateFormat")
fun getLogs(output: String, error: String, command: String): Logs {
    val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())
    val timeLine = "--------$currentTime--------\n"
    val commandText = "> $command\n"
    val sessionLog = "$timeLine$commandText$output$error\n---------------------------------------\n\n"
    val outputLog  = "$timeLine$commandText$output\n---------------------------------------\n\n"
    val errorLog   = "$timeLine$commandText$error\n---------------------------------------\n\n"
    if (output.isNotBlank() || error.isNotBlank())
        SESSION_LOG += sessionLog
    if (output.isNotBlank())
        OUTPUT_LOG += outputLog
    if (error.isNotBlank())
        ERROR_LOG += errorLog

    return Logs(SESSION_LOG, OUTPUT_LOG, ERROR_LOG)
}

fun appendSessionLog(output: String): String {
    val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())
    val timeLine = "--------$currentTime--------\n"
    val sessionLog = "$timeLine$output\n---------------------------------------\n\n"
    if (output.isNotBlank())
        SESSION_LOG += sessionLog
    return SESSION_LOG
}

fun makeACall(number: String, serialNumber: String) {
    val command = "-s $serialNumber shell am start -a android.intent.action.CALL -d tel:$number"
    val output = RunCommand.adb(command)
//    Log.d(TAG, "makeACall: command: adb $command")
    Log.d(TAG, "makeACall: output: $output")
}