package com.kanha.devicecontrol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.ActionSelectionCard
import com.kanha.devicecontrol.composables.CallingDialog
import com.kanha.devicecontrol.composables.ImageDialog
import com.kanha.devicecontrol.composables.PowerMenuDialog
import com.kanha.devicecontrol.composables.Toolbar
import com.kanha.devicecontrol.extensions.trimEverythingExceptPlusAndNumbers
import com.kanha.devicecontrol.models.Actions
import com.kanha.devicecontrol.models.PowerControlItem
import com.kanha.devicecontrol.operations.PowerState.*
import com.kanha.devicecontrol.operations.ext
import com.kanha.devicecontrol.operations.getDeviceAndModel
import com.kanha.devicecontrol.operations.powerControl
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH_ROOT
import com.kanha.devicecontrol.util.OPERATION
import com.kanha.devicecontrol.util.Operation.PULL
import com.kanha.devicecontrol.util.Operation.PUSH
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import com.kanha.devicecontrol.util.makeACall

var showCallingDialog by mutableStateOf(false)
var showPowerMenuDialog by mutableStateOf(false)
var showImageDialog by mutableStateOf(false)

private const val TAG = "ActionSelectionActivity"

class ActionSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actions = arrayListOf(
            Actions(
                name = "Dialer",
                description = "Make a call with connected device",
                icon = R.drawable.baseline_call_24,
                action = {
                    showCallingDialog = true
                }
            ),
            Actions(
                name = "File Explorer",
                description = "Browse files on connected device",
                icon = R.drawable.baseline_folder_special_24,
                action = {
                    OPERATION = PULL
                    CURRENT_FILE_EXPLORER_PATH = DEFAULT_FILE_EXPLORER_PATH
                    ext = "all"
                    startActivity(Intent(this, FileExplorerActivity::class.java))
                }
            ),
            Actions(
                name = "File Uploader",
                description = "Upload files to connected device",
                icon = R.drawable.baseline_upload_file_24,
                action = {
                    OPERATION = PUSH
                    CURRENT_FILE_EXPLORER_PATH = DEFAULT_FILE_EXPLORER_PATH_ROOT
                    ext = "all"
                    startActivity(Intent(this, FileExplorerActivity::class.java))
                }
            ),
            Actions(
                name = "Messaging",
                description = "Send a text message with connected device",
                icon = R.drawable.baseline_message_24,
                action = {
                    startActivity(Intent(this, MessagingActivity::class.java))
                }
            ),
            Actions(
                name = "App Manager",
                description = "Manage apps on connected device",
                icon = R.drawable.baseline_apps_24,
                action = {
                    startActivity(Intent(this, AppControlActivity::class.java))
                }
            ),
            Actions(
                name = "Power Menu",
                description = "Reboot, Shutdown, etc. connected device",
                icon = R.drawable.round_settings_power_24,
                action = {
                    showPowerMenuDialog = true
                }
            )
        )


        val powerControlItems = arrayListOf(
            PowerControlItem(
                name = "Reboot",
                icon = R.drawable.round_restart_alt_24,
                onClick = {
                    powerControl(REBOOT, this@ActionSelectionActivity)
                }
            ),
            PowerControlItem(
                name = "Shutdown",
                icon = R.drawable.round_power_settings_new_24,
                onClick = {
                    powerControl(SHUTDOWN, this@ActionSelectionActivity)
                }
            ),
            PowerControlItem(
                name = "Recovery",
                icon = R.drawable.baseline_restore_24,
                onClick = {
                    powerControl(RECOVERY, this@ActionSelectionActivity)
                }
            ),
            PowerControlItem(
                name = "Bootloader",
                icon = R.drawable.sharp_settings_24,
                onClick = {
                    powerControl(BOOTLOADER, this@ActionSelectionActivity)
                }
            ),
            PowerControlItem(
                name = "Lock",
                icon = R.drawable.outline_screen_lock_portrait_24,
                onClick = {
                    powerControl(LOCK, this@ActionSelectionActivity)
                }
            ),
            PowerControlItem(
                name = "Screenshot",
                icon = R.drawable.outline_screenshot_24,
                onClick = {
                    powerControl(SCREENSHOT, this@ActionSelectionActivity)
                }
            ),
        )

        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        Toolbar(
                            context = this@ActionSelectionActivity,
                            onRefresh = { refresh() },
                            title = getDeviceAndModel(SELECTED_DEVICE)
                        )
                        LazyColumn(
                            modifier = Modifier.padding(top = 24.dp)
                        ) {
                            items(actions) {
                                ActionSelectionCard(
                                    icon = painterResource(id = it.icon),
                                    name = it.name,
                                    description = it.description,
                                    onClick = it.action
                                )
                                if (showCallingDialog) {
                                    CallingDialog(onDismissRequest = { showCallingDialog = false },
                                        onSubmit = { text ->
                                            showCallingDialog = false
                                            makeACall(
                                                text.trimEverythingExceptPlusAndNumbers(),
                                                SELECTED_DEVICE
                                            )
                                        }
                                    )
                                }
                                PowerMenuDialog(
                                    showDialog = showPowerMenuDialog,
                                    onDismiss = { showPowerMenuDialog = false },
                                    powerControlItems = powerControlItems
                                )
                                ImageDialog(
                                    context = this@ActionSelectionActivity,
                                    showDialog = showImageDialog,
                                    onDismiss = { showImageDialog = false }
                                )
                            }
                        }
//                        Text(
//                            text = "More Features Coming Soon!!",
//                            modifier = Modifier.padding(start = 16.dp),
//                        )
                    }
                }
            }
        }
    }
}
