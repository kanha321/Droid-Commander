package com.kanha.devicecontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanha.devicecontrol.composables.LongClickMenu
import com.kanha.devicecontrol.customs.MyToast
import com.kanha.devicecontrol.models.DropDownItem
import com.kanha.devicecontrol.ui.theme.DeviceControlTheme

class TempActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeviceControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        items(
                            listOf(
                                "Hello",
                                "World",
                                "This",
                                "Is",
                                "A",
                                "Test",
                                "List",
                                "Of",
                                "Strings"
                            )
                        ){
                            LongClickMenu(
                                name = it,
                                dropDownItems = arrayListOf(
                                    DropDownItem(text = "Download"),
                                    DropDownItem(text = "Delete"),
                                    DropDownItem(text = "Rename"),
                                ),
                                onItemClick = {
                                    MyToast.show(this@TempActivity, it.text)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}