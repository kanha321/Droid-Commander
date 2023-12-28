package com.kanha.devicecontrol.util

import android.app.Application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kanha.devicecontrol.MainActivity
import com.kanha.devicecontrol.operations.getSerialNumbers

var ROOT_MODE: Boolean = true

var COUNTRY_CODE by mutableStateOf("+91")

var SESSION_LOG by mutableStateOf("session.log\n\n")
var OUTPUT_LOG by mutableStateOf("output.log\n\n")
var ERROR_LOG by mutableStateOf("error.log\n\n")
var SELECTED_LOG_TYPE by mutableStateOf("Session Log")

var IS_DEVELOPER_MODE_ENABLED by mutableStateOf(false)
var SELECTED_DEVICE by mutableStateOf("")
var SELECTED_DEVICE_MODEL by mutableStateOf("")
var IS_DEVICE_CONNECTED by mutableStateOf(getSerialNumbers().isNotEmpty())

// file explorer

var DEFAULT_FILE_EXPLORER_PATH = "/storage/emulated/0/"
var DEFAULT_FILE_EXPLORER_PATH_ROOT = "/"
var CURRENT_FILE_EXPLORER_PATH by mutableStateOf("")
var SELECTED_FILE by mutableStateOf("")
var SELECTED_FILE_WITH_PATH by mutableStateOf(CURRENT_FILE_EXPLORER_PATH + SELECTED_FILE)
// var pullDestinationPath = "/storage/emulated/0/DeviceControl/pulled/"

var DEFAULT_PUSH_DESTINATION_PATH = "/storage/emulated/0/DeviceControl/"
var DEFAULT_PULL_DESTINATION_PATH = "/storage/emulated/0/DeviceControl/pulled/"
var PULL_DESTINATION_PATH by mutableStateOf(DEFAULT_PULL_DESTINATION_PATH)
var PUSH_DESTINATION_PATH by mutableStateOf(DEFAULT_PUSH_DESTINATION_PATH)
// some bad resources

var OPERATION by mutableStateOf(Operation.NULL)

enum class Operation {
    PULL, PUSH, INSTALL, NULL
}