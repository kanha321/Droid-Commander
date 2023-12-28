package com.kanha.devicecontrol.models

import androidx.compose.ui.graphics.Color
import com.kanha.devicecontrol.operations.getRandomColor


data class Message(
    val row: String,
    val id: String,
    val threadId: String,
    val address: String,
    val person: String,
    val date: String,
    val dateSent: String,
    val protocol: String,
    val read: String,
    val status: String,
    val type: String,
    val replyPathPresent: String,
    val subject: String,
    val body: String,
    val serviceCenter: String,
    val locked: String,
    val subId: String,
    val errorCode: String,
    val creator: String,
    val seen: String,
    val color: Color = getRandomColor()
) {
    override fun toString(): String {
        return "Message(" +
                    "row='$row', " +
                    "id='$id', " +
                    "threadId='$threadId', " +
                    "address='$address', " +
                    "person='$person', " +
                    "date='$date', " +
                    "dateSent='$dateSent', " +
                    "protocol='$protocol', " +
                    "read='$read', " +
                    "status='$status', " +
                    "type='$type', " +
                    "replyPathPresent='$replyPathPresent', " +
                    "subject='$subject', " +
                    "body='$body', " +
                    "serviceCenter='$serviceCenter', " +
                    "locked='$locked', " +
                    "subId='$subId', " +
                    "errorCode='$errorCode', " +
                    "creator='$creator', " +
                    "seen='$seen'" +
                ")"
    }
}