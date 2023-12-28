package com.kanha.devicecontrol.composables

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.util.CURRENT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH
import com.kanha.devicecontrol.util.DEFAULT_FILE_EXPLORER_PATH_ROOT
import com.kanha.devicecontrol.util.OPERATION
import com.kanha.devicecontrol.util.Operation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerToolbar(
    context: Context,
    onRefresh: () -> Unit
) {
    TopAppBar(
        title = {
            val scrollState = rememberScrollState()

            // Scroll to the end whenever the text changes
            LaunchedEffect(CURRENT_FILE_EXPLORER_PATH) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }

            Text(
                text = CURRENT_FILE_EXPLORER_PATH,
                modifier = Modifier.horizontalScroll(scrollState)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        navigationIcon = {
            IconButton(onClick = {
                if (OPERATION == Operation.PULL && CURRENT_FILE_EXPLORER_PATH != DEFAULT_FILE_EXPLORER_PATH) {
                    CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                    CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                    CURRENT_FILE_EXPLORER_PATH += "/"
                } else if (OPERATION == Operation.PUSH || OPERATION == Operation.INSTALL && CURRENT_FILE_EXPLORER_PATH != DEFAULT_FILE_EXPLORER_PATH_ROOT) {

                    if (CURRENT_FILE_EXPLORER_PATH == DEFAULT_FILE_EXPLORER_PATH) {
                        CURRENT_FILE_EXPLORER_PATH = "/storage/"
                    } else {
                        CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                        CURRENT_FILE_EXPLORER_PATH = CURRENT_FILE_EXPLORER_PATH.substringBeforeLast("/")
                        CURRENT_FILE_EXPLORER_PATH += "/"
                    }
                } else {
                    (context as Activity).finish()
                    Handler().postDelayed({
                        CURRENT_FILE_EXPLORER_PATH = DEFAULT_FILE_EXPLORER_PATH
                    }, 500)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
        actions = {
            IconButton(onClick = {
                // copy path to clipboard
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("path", CURRENT_FILE_EXPLORER_PATH)
                clipboard.setPrimaryClip(clip)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_link_24),
                    contentDescription = "Copy Path",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            IconButton(onClick = {
                CURRENT_FILE_EXPLORER_PATH = "${CURRENT_FILE_EXPLORER_PATH}Downloads/"
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = "Refresh",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    )
}