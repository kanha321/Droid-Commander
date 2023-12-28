package com.kanha.devicecontrol.composables

import android.content.Context
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.TempActivity
import com.kanha.devicecontrol.TerminalActivity
import com.kanha.devicecontrol.customs.MyToast
import com.kanha.devicecontrol.operations.checkRootOnHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    context: Context,
    onRefresh: () -> Unit,
    title: String = context.getString(R.string.app_name)
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        actions =
        {
            IconButton(onClick = {
                // check if device is rooted
                try {
                    MyToast.show(context, checkRootOnHost())
                } catch (e: Exception) {
                    MyToast.show(
                        context,
                        "Device probably not Rooted"
                    )
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.round_tag_24),
                    contentDescription = "check Root",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            IconButton(onClick = {
                onRefresh()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = "Refresh",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            IconButton(onClick = {
                startActivity(context, Intent(context, TerminalActivity::class.java), null)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_terminal_24),
                    contentDescription = "Terminal",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    )
}