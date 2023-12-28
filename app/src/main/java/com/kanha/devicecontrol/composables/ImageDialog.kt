package com.kanha.devicecontrol.composables

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.io.File

private const val TAG = "ImageDialog"

@Composable
fun ImageDialog(
    context: Context,
    file: File = File("${context.cacheDir.path}/screenshot.png"),
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    // take not found image from assets
    val notFound = BitmapFactory.decodeStream(context.assets.open("images-picsay.png")).asImageBitmap()
    Log.d(TAG, "ImageDialog: ${file.path}")
    if (showDialog){
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Image(
                bitmap = try {
                    BitmapFactory.decodeFile(file.path).asImageBitmap()
                } catch (e: Exception) {
                    Log.e(TAG, "ImageDialog: ${e.message}")
                    notFound
                },
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}