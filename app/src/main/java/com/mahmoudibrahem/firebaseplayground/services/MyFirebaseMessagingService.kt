package com.mahmoudibrahem.firebaseplayground.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.util.Constants.NOTIFICATION_CHANNEL_DESCRIPTION
import com.mahmoudibrahem.firebaseplayground.util.Constants.NOTIFICATION_CHANNEL_ID
import com.mahmoudibrahem.firebaseplayground.util.Constants.NOTIFICATION_CHANNEL_NAME
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //Send the token to the backend server if exists (I have no server to send the token to)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.data["title"]
        val content = message.data["body"]
        if (title != null && content != null) {
            createNotification(title, content)
        }
        super.onMessageReceived(message)
    }

    private fun createNotification(
        title: String,
        content: String
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
                createNotificationChannel(notificationManager)
            }
        }
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(R.mipmap.ic_launcher)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        notificationManager.notify(0, builder.build())
    }

    private fun createNotificationChannel(nm: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appNotificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = NOTIFICATION_CHANNEL_DESCRIPTION }
            nm.createNotificationChannel(appNotificationChannel)
        }
    }
}