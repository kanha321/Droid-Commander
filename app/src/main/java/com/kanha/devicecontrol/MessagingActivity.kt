package com.kanha.devicecontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.MessageCard
import com.kanha.devicecontrol.operations.getAllMessages
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme

private const val TAG = "MessagingActivity"

class MessagingActivity : ComponentActivity() {

    private val messages = getAllMessages()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        items(messages) {
                            MessageCard(message = it)
                        }
                    }
                }
            }
        }
    }
}