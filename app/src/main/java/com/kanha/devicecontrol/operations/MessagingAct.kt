package com.kanha.devicecontrol.operations

import com.kanha.devicecontrol.models.Message
import com.kanha.devicecontrol.util.RunCommand
import com.kanha.devicecontrol.util.SELECTED_DEVICE
import androidx.compose.ui.graphics.Color

fun getAllMessages(): ArrayList<Message> {
    val output = RunCommand.adb("-s $SELECTED_DEVICE shell content query --uri content://sms")
    return populateMessageArraylistWith(output)
}

fun populateMessageArraylistWith(output: String): ArrayList<Message> {
    val messages = ArrayList<Message>()
    val output = "\n" + output

    val rows = output.split("\nRow: ")
    for (row in rows) {
        if (row.isBlank()) continue
        val messageRow = row.substring(0, 1)
        val messageId = row.substring(row.indexOf(" _id=") + 5, row.indexOf(", thread_id="))
        val messageThreadId = row.substring(row.indexOf(", thread_id=") + 12, row.indexOf(", address="))
        val messageAddress = row.substring(row.indexOf(", address=") + 10, row.indexOf(", person="))
        val messagePerson = row.substring(row.indexOf(", person=") + 9, row.indexOf(", date="))
        val messageDate = row.substring(row.indexOf(", date=") + 7, row.indexOf(", date_sent="))
        val messageDateSent = row.substring(row.indexOf(", date_sent=") + 12, row.indexOf(", protocol="))
        val messageProtocol = row.substring(row.indexOf(", protocol=") + 11, row.indexOf(", read="))
        val messageRead = row.substring(row.indexOf(", read=") + 7, row.indexOf(", status="))
        val messageStatus = row.substring(row.indexOf(", status=") + 9, row.indexOf(", type="))
        val messageType = row.substring(row.indexOf(", type=") + 7, row.indexOf(", reply_path_present="))
        val messageReplyPathPresent = row.substring(row.indexOf(", reply_path_present=") + 20, row.indexOf(", subject="))
        val messageSubject = row.substring(row.indexOf(", subject=") + 10, row.indexOf(", body="))
        val messageBody = row.substring(row.indexOf(", body=") + 7, row.lastIndexOf(", service_center="))
        val messageServiceCenter = row.substring(row.lastIndexOf(", service_center=") + 17, row.lastIndexOf(", locked="))
        val messageLocked = row.substring(row.lastIndexOf(", locked=") + 9, row.lastIndexOf(", sub_id="))
        val messageSubId = row.substring(row.lastIndexOf(", sub_id=") + 9, row.lastIndexOf(", error_code="))
        val messageErrorCode = row.substring(row.lastIndexOf(", error_code=") + 13, row.lastIndexOf(", creator="))
        val messageCreator = row.substring(row.lastIndexOf(", creator=") + 10, row.lastIndexOf(", seen="))
        val messageSeen = row.substring(row.lastIndexOf(", seen=") + 7)
        messages.add(Message(messageRow, messageId, messageThreadId, messageAddress, messagePerson, messageDate, messageDateSent, messageProtocol, messageRead, messageStatus, messageType, messageReplyPathPresent, messageSubject, messageBody, messageServiceCenter, messageLocked, messageSubId, messageErrorCode, messageCreator, messageSeen))
    }
    return messages
}

fun getRandomColor(): Color {
    // this function is to generate a random color from pink, purple, orange, cyan and red in same shades that google uses in their apps
    val colors = arrayOf("#F06292", "#BA68C8", "#FFB74D", "#4DD0E1", "#EF5350")
    return Color(android.graphics.Color.parseColor(colors.random()))
}