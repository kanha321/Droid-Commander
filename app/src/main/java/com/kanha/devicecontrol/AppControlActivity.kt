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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.AppsCard
import com.kanha.devicecontrol.composables.FAB
import com.kanha.devicecontrol.composables.Toolbar
import com.kanha.devicecontrol.operations.ext
import com.kanha.devicecontrol.operations.getInstalledPackages
import com.kanha.devicecontrol.operations.launchApp
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH_ROOT
import com.kanha.devicecontrol.util.OPERATION
import com.kanha.devicecontrol.util.Operation.*

class AppControlActivity : ComponentActivity() {
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
                        Toolbar(
                            context = this@AppControlActivity,
                            onRefresh = {
                                getInstalledPackages()
                            },
                            title = "App Control"
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                        ) {
                            items(getInstalledPackages()) {
                                AppsCard(
                                    packageName = it,
                                    onClick = {
                                        launchApp(it)
                                    }
                                )
                            }
                        }
                    }
                }
                FAB(
                    onClick = {
                        OPERATION = INSTALL
                        CURRENT_FILE_EXPLORER_PATH = DEFAULT_FILE_EXPLORER_PATH
                        ext = ".apk"
                        startActivity(Intent(this, FileExplorerActivity::class.java))
                    }
                )
            }
        }
    }
}