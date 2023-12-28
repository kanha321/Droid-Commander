package com.kanha.devicecontrol.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanha.devicecontrol.R
import com.kanha.devicecontrol.models.Message
import com.kanha.devicecontrol.operations.getRandomColor
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date
import java.util.Locale

@Composable
fun MessageCard(message: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 2.dp, bottom = 2.dp, start = 12.dp, end = 12.dp),
        ) {
            Image(
                painter = rememberVectorPainter(image = Icons.Default.Person),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(45.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(message.color)
                    .padding(6.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = message.address,
                        style = TextStyle(
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 20.sp,
                            fontWeight = if (message.read == "1") FontWeight.Normal else FontWeight.Bold,
                        )
                    )
                    Row {
                        Text(
                            text = PrettyTime(Locale.getDefault()).format(Date(message.date.toLong())),
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                lineHeight = 20.sp,
                                fontWeight = if (message.read == "1") FontWeight.Normal else FontWeight.Bold,
                            )
                        )
                    }
                }
                Text(
                    text = message.body,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 20.sp,
                        fontWeight = if (message.read == "1") FontWeight.Normal else FontWeight.Bold,
                    ),
                    maxLines = if(message.read == "1") 1 else 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}