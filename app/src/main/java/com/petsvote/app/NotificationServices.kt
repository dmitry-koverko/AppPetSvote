package com.petsvote.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.petsvote.app.notification.isVoteNotify
import com.petsvote.app.notification.toLocalBigNotifyMessage
import com.petsvote.app.notification.toLocalNotifyMessage

class NotificationServices: FirebaseMessagingService() {
    private var title = ""
    private var description = ""
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (BuildConfig.DEBUG) Log.d(NotificationServices::class.java.canonicalName, token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (BuildConfig.DEBUG) Log.d(NotificationServices::class.java.canonicalName, message.data.toString())

        Log.d("NotificationServices", message.data.toString())
        val channelId = "Default"

        if(message.isVoteNotify()){
            title = message.toLocalBigNotifyMessage(applicationContext)?.title ?: ""
            description = message.toLocalBigNotifyMessage(applicationContext)?.description ?: ""
        }else{
            title = message.toLocalNotifyMessage()?.body ?: ""
            description = message.toLocalNotifyMessage()?.title ?: ""
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationLayout = RemoteViews(packageName, R.layout.notification_collapsed)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_expanded)

        notificationLayout.setTextViewText(R.id.notification_title, title)
        notificationLayout.setTextViewText(R.id.notification_description, description)

        val name = getString(com.petsvote.ui.R.string.app_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("DEFAULT", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val customNotification = NotificationCompat.Builder(baseContext, "DEFAULT")
            .setSmallIcon(com.petsvote.ui.R.drawable.icon)
            .setCustomContentView(notificationLayout)
            //.setCustomBigContentView(notificationLayoutExpanded)
            .setContentIntent(pendingIntent)
            .build()


        NotificationManagerCompat.from(baseContext).apply {
            notificationManager.notify(0, customNotification)
        }
    }

}