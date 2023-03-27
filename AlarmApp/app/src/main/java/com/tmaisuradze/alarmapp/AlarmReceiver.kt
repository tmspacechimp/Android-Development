package com.tmaisuradze.alarmapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let {
            val notificationManager = NotificationManagerCompat.from(p0)
            val time = p1?.getStringExtra("time")
            val requestCode =
                (time?.substringBefore(":")?.toInt() ?: 0) * 60 +
                        (time?.substringAfter(":")?.toInt() ?: 0)

            if (p1 != null) {
                if (p1.action == CANCEL_ACTION_NAME) {
                    notificationManager.cancel(requestCode)
                    return
                }
            }

            val notificationClickPendingIntent = PendingIntent.getActivity(
                p0,
                requestCode,
                Intent(p0, MainActivity::class.java),
                0
            )

            val cancelButtonClick = PendingIntent.getBroadcast(
                p0,
                requestCode,
                Intent(CANCEL_ACTION_NAME).apply {
                    `package` = p0.packageName
                    putExtra("time", time)
                },
                0
            )

            val snoozeButtonClick = PendingIntent.getBroadcast(
                p0,
                requestCode,
                Intent(SNOOZE_ACTION_NAME).apply {
                    `package` = p0.packageName
                    putExtra("time", time)
                },
                0
            )

            createChannel(notificationManager)
            val notification = NotificationCompat.Builder(p0, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_clock)
                .setContentTitle("Alarm Message!")
                .setContentText("Alarm set on $time")
                .setContentIntent(notificationClickPendingIntent)
                .addAction(R.mipmap.ic_launcher, "CANCEL", cancelButtonClick)
                .addAction(R.mipmap.ic_launcher, "SNOOZE", snoozeButtonClick)
                .build()


            var delayMillis: Long = 0
            if (p1?.action == SNOOZE_ACTION_NAME) {
                delayMillis = 60000
                notificationManager.cancel(requestCode)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                notificationManager.notify(requestCode, notification)
            }, delayMillis)
        }
    }

    companion object{
        const val CANCEL_ACTION_NAME = "com.tmaisuradze.alarmapp.main.CANCEL_ACTION"
        const val SNOOZE_ACTION_NAME = "com.tmaisuradze.alarmapp.main.SNOOZE_ACTION"
        const val ALARM_ACTION_NAME = "com.tmaisuradze.alarmapp.main.ALARM_ACTION"
        const val CHANNEL_ID = "com.tmaisuradze.alarmapp.main.CHANNEL_1"
    }

    private fun createChannel(notificationManager: NotificationManagerCompat) {
        val channel = NotificationChannel(CHANNEL_ID, "channel_name",
            NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

}