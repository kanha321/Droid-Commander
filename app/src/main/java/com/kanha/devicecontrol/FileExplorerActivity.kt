package com.kanha.devicecontrol

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.FileExplorerCard
import com.kanha.devicecontrol.composables.FileExplorerToolbar
import com.kanha.devicecontrol.operations.ext
import com.kanha.devicecontrol.operations.lsClient
import com.kanha.devicecontrol.operations.lsHost
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH_ROOT
import com.kanha.devicecontrol.util.OPERATION
import com.kanha.devicecontrol.util.Operation.*

class FileExplorerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        FileExplorerToolbar(
                            context = this@FileExplorerActivity,
                            onRefresh = { refresh() }
                        )
                        LazyColumn(
                            modifier = Modifier.padding(top = 24.dp)
                        ) {
                            items(if (OPERATION == PULL) lsClient(ext = ext) else lsHost(ext = ext)) {
                                FileExplorerCard(
                                    type = it.type,
                                    permissions = it.permissions,
                                    hardLinks = it.hardLinks,
                                    owner = it.owner,
                                    group = it.group,
                                    size = it.size,
                                    date = it.date,
                                    time = it.time,
                                    name = it.name,
                                    context = this@FileExplorerActivity,
//                                    dropDownItems = arrayListOf(
//                                        DropDownItem(text = "Download"),
//                                        DropDownItem(text = "Delete"),
//                                        DropDownItem(text = "Rename"),
//                                    ),
//                                    onItemClick = {
//                                        if (it.text == "Download") {
//                                            MyToast.show(this@FileExplorerActivity, "Downloading...$selectedFileWithPath")
//                                        } else if (it.text == "Delete") {
//                                            MyToast.show(this@FileExplorerActivity, "Deleting...")
//                                        } else if (it.text == "Rename") {
//                                            MyToast.show(this@FileExplorerActivity, "Renaming...")
//                                        } else{
//                                            MyToast.show(this@FileExplorerActivity, "This is not possible")
//                                        }
//                                    }
                                )
//                                FileOperations(
//                                    name = currentFileExplorerPath + it.name,
//                                    dropDownItems = arrayListOf(
//                                        DropDownItem(text = "Download"),
//                                        DropDownItem(text = "Delete"),
//                                        DropDownItem(text = "Rename"),
//                                    ),
//                                    onItemClick = {
//                                        MyToast.show(this@FileExplorerActivity, it.text)
//                                    }
//                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (OPERATION == PULL) {
            if (CURRENT_FILE_EXPLORER_PATH != DEFAULT_FILE_EXPLORER_PATH) {
                CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                CURRENT_FILE_EXPLORER_PATH += "/"
                return
            }
        } else if (OPERATION == PUSH || OPERATION == INSTALL) {
            if (CURRENT_FILE_EXPLORER_PATH == DEFAULT_FILE_EXPLORER_PATH) {
                CURRENT_FILE_EXPLORER_PATH = "/storage/"
                return
            }
            if (CURRENT_FILE_EXPLORER_PATH != DEFAULT_FILE_EXPLORER_PATH_ROOT) {
                CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                CURRENT_FILE_EXPLORER_PATH += "/"
                return
            }
        }
        super.onBackPressed()
        Handler().postDelayed({
            CURRENT_FILE_EXPLORER_PATH = DEFAULT_FILE_EXPLORER_PATH
        }, 500)
    }
}