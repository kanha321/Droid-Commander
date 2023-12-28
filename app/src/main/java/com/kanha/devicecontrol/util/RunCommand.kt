package com.kanha.devicecontrol.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object RunCommand {

    private const val TAG = "RunCommand"

    fun shell(
        command: String,
        asRoot: Boolean = ROOT_MODE,
        updateSessionLog: Boolean = true
    ): String {
        var newCommand: String = command
        var newAsRoot: Boolean = asRoot
        if (command.startsWith("adb")) {
            newCommand = command.replace("adb", "/data/adb/bin/adb")
            newAsRoot = true
        } else if (command.startsWith("fastboot")) {
            newCommand = command.replace("fastboot", "/data/adb/bin/fastboot")
            newAsRoot = true
        }
        return try {
            val process: Process
            if (newAsRoot) {
                process = Runtime.getRuntime().exec("su")
                process.outputStream.write(newCommand.toByteArray())
                process.outputStream.flush()
                process.outputStream.close()
            } else {
                process = Runtime.getRuntime().exec(newCommand)
            }

            val output = StringBuilder()
            val error = StringBuilder()

            val outputThread = Thread {
                val reader = BufferedReader(
                    InputStreamReader(process.inputStream)
                )
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
                reader.close()
            }
            outputThread.start()

            val errorThread = Thread {
                val errorReader = BufferedReader(
                    InputStreamReader(process.errorStream)
                )
                var line: String?
                while (errorReader.readLine().also { line = it } != null) {
                    error.append(line).append("\n")
                }
                errorReader.close()
            }
            errorThread.start()

            outputThread.join()
            errorThread.join()

            process.waitFor()

            if (updateSessionLog && !command.contains("getprop"))
                getLogs(output.toString().trim(), error.toString().trim(), command.trim())
//                SESSION_LOG += "$output$error----------------------------------\n"

            return error.toString().trim() + output.toString().trim()

        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

//    suspend fun adb(command: String, updateSessionLog: Boolean = true): String {
//        return withContext(Dispatchers.IO) {
//            val output = StringBuilder()
//            val error = StringBuilder()
//            try {
//                val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "/data/adb/bin/adb $command"))
//                val reader = BufferedReader(InputStreamReader(process.inputStream))
//                var line: String?
//                while (reader.readLine().also { line = it } != null) {
//                    output.append(line).append("\n")
//                }
//                reader.close()
//                val errorReader = BufferedReader(InputStreamReader(process.errorStream))
//                while (errorReader.readLine().also { line = it } != null) {
//                    error.append(line).append("\n")
//                }
//                errorReader.close()
//                process.waitFor()
//            } catch (e: IOException) {
//                e.printStackTrace()
//                return@withContext e.message ?: "IOException occurred"
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//                return@withContext e.message ?: "InterruptedException occurred"
//            }
//            if (updateSessionLog)
//                getLogs(output.toString().trim(), error.toString().trim())
//
//            return@withContext error.toString().trim() + output.toString().trim()
//        }
//    }



//    fun adb(command: String): Pair<Boolean, String> {
//        try {
//            val cmd = arrayOf("su", "-c", "/data/data/com.termux/files/usr/bin/adb $command")
//
//            val process = Runtime.getRuntime().exec("su")
//            process.outputStream.write(command.toByteArray())
//            process.outputStream.flush()
//            process.outputStream.close()
//
//            val reader = BufferedReader(InputStreamReader(process.inputStream))
//            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
//            val output = StringBuilder()
//            var line: String?
//
//            // Read input stream
//            while (reader.readLine().also { line = it } != null) {
//                output.append(line).append("\n")
//            }
//
//            // Check for errors
//            val errors = errorReader.readText()
//            if (errors.isNotEmpty()) {
//                Log.e("TAG", "ADB command error: $errors")
//                return Pair(false, "An error occurred while executing the command: $command")
//            }
//
//            // Wait for process to finish
//            val exitCode = process.waitFor()
//
//            // Return output
//            Log.d("TAG", "ADB command output: $output")
//            return Pair(true, output.toString())
//        } catch (e: IOException) {
//            e.printStackTrace()
//            return Pair(false, "IOException while executing the command: $command")
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//            return Pair(false, "InterruptedException while executing the command: $command")
//        }
//    }







    // this code works

    fun adb(
        command: String,
        updateSessionLog: Boolean = true,
        returnOnlyOutput: Boolean = false
    ): String {
        val output = StringBuilder()
        val error = StringBuilder()
        try {
            val process =
                Runtime.getRuntime().exec(arrayOf("su", "-c", "/data/adb/bin/adb $command"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            reader.close()
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
            while (errorReader.readLine().also { line = it } != null) {
                error.append(line).append("\n")
            }
            errorReader.close()
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
            return e.message ?: "IOException occurred"
        } catch (e: InterruptedException) {
            e.printStackTrace()
            return e.message ?: "InterruptedException occurred"
        }
        if (updateSessionLog && !command.contains("getprop"))
            getLogs(output.toString().trim(), error.toString().trim(), "adb ${command.trim()}")

        return if (returnOnlyOutput) output.toString().trim() else error.toString().trim() + output.toString().trim()
    }

    fun fastboot(command: String, updateSessionLog: Boolean = true): String {
        val output = StringBuilder()
        val error = StringBuilder()
        try {
            val process =
                Runtime.getRuntime().exec(arrayOf("su", "-c", "/data/adb/bin/fastboot $command"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            reader.close()
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
            while (errorReader.readLine().also { line = it } != null) {
                error.append(line).append("\n")
            }
            errorReader.close()
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
            return e.message ?: "IOException occurred"
        } catch (e: InterruptedException) {
            e.printStackTrace()
            return e.message ?: "InterruptedException occurred"
        }
        if (updateSessionLog)
            getLogs(output.toString().trim(), error.toString().trim(), "fastboot ${command.trim()}")

        return error.toString().trim() + output.toString().trim()
    }

//    fun adb(command: String): String {
//        val file = File("/data/data/com.kanha.devicecontrol/files/assets/adb")
//
//        if (!file.canExecute()){
//            file.setExecutable(true)
//        } else {
//            Log.d(TAG, "adb: can execute")
//        }
//
//        val output = StringBuilder()
//        val error = StringBuilder()
//        try {
//            val process = Runtime.getRuntime()
//                .exec(arrayOf(file.path, command))
//            val reader = BufferedReader(InputStreamReader(process.inputStream))
//            var line: String?
//            while (reader.readLine().also { line = it } != null) {
//                output.append(line).append("\n")
//            }
//            reader.close()
//            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
//            while (errorReader.readLine().also { line = it } != null) {
//                error.append(line).append("\n")
//            }
//            errorReader.close()
//            process.waitFor()
//        } catch (e: IOException) {
//            e.printStackTrace()
//            return e.message ?: "IOException occurred"
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//            return e.message ?: "InterruptedException occurred"
//        }
//        SESSION_LOG += "$error$output----------------------------------\n"
//        return error.toString().trim() + output.toString().trim()
//    }

}