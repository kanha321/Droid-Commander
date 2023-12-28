package com.kanha.devicecontrol

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.DropDownMenu
import com.kanha.devicecontrol.composables.ScrollableText
import com.kanha.devicecontrol.extensions.clearString
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme
import com.kanha.devicecontrol.util.ERROR_LOG
import com.kanha.devicecontrol.util.OUTPUT_LOG
import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.SELECTED_LOG_TYPE
import com.kanha.devicecontrol.util.SESSION_LOG

var commandsText by mutableStateOf("")
var output by mutableStateOf("")

private const val TAG = "TerminalActivity"

class TerminalActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        var logType by remember {
                            mutableStateOf(SESSION_LOG)
                        }
                        ScrollableText(
                            output = when (SELECTED_LOG_TYPE) {
                                "Session Log" -> SESSION_LOG
                                "Output Log" -> OUTPUT_LOG
                                "Error Log" -> ERROR_LOG
                                else -> SESSION_LOG
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter),
                            onClick = {
                                // copy logs to clipboard
                                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = when (SELECTED_LOG_TYPE) {
                                    "Session Log" -> ClipData.newPlainText("Session Log", SESSION_LOG)
                                    "Output Log" -> ClipData.newPlainText("Output Log", OUTPUT_LOG)
                                    "Error Log" -> ClipData.newPlainText("Error Log", ERROR_LOG)
                                    else -> ClipData.newPlainText("Error", "Something went wrong")
                                }
                                clipboard.setPrimaryClip(clip)
                            }
                        )
                        Column {
                            DropDownMenu(
                                items = ArrayList<String>().apply {
                                    add("Session Log")
                                    add("Output Log")
                                    add("Error Log")
                                },
                                label = "Session Log",
                                onValueChange = {
                                    Log.d(TAG, "onCreate DropDownMenu: $logType")
                                    logType = when (it) {
                                        "Session Log" -> SESSION_LOG
                                        "Output Log" -> OUTPUT_LOG
                                        "Error Log" -> ERROR_LOG
                                        else -> SESSION_LOG
                                    }
                                }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 0.dp,
                                        bottom = 20.dp,
                                        start = 20.dp,
                                        end = 20.dp
                                    ),
                                value = commandsText,
                                onValueChange = {
                                    commandsText = it
                                },
                                singleLine = true,
                                label = { Text("Run Shell Commands") },
                                readOnly = false,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Text
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        output = RunCommand.shell(commandsText)
                                        if (commandsText == "clear") {
                                            SESSION_LOG = "session.log\n\n"
                                            OUTPUT_LOG = "output.log\n\n"
                                            ERROR_LOG = "error.log\n\n"
                                        }
                                        commandsText = ""
                                    }
                                )
                            )
                            Text(text = output.clearString())
                        }
                    }
                }
            }
        }
    }
}