package com.example.clickup.br

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.clickup.MainActivity
import com.example.clickup.R
import com.example.clickup.model.TaskInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    private var notificationManager: NotificationManagerCompat? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingPermission")
    override fun onReceive(p0: Context?, p1: Intent?) {
        val taskInfo = p1?.getSerializableExtra("task_info") as? TaskInfo
        if (sharedPreferences.getBoolean(taskInfo?.priority.toString(), true)) {
            val tapResultIntent = Intent(p0, MainActivity::class.java)
            tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent: PendingIntent =
                getActivity(p0, 0, tapResultIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

            val intent1 = Intent(p0, OnCompletedBroadcastReceiver::class.java).apply {
                putExtra("task_info", taskInfo)
            }
            val pendingIntent1: PendingIntent? =
                taskInfo?.let {
                    getBroadcast(
                        p0,
                        it.id,
                        intent1,
                        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                    )
                }
            val action1: NotificationCompat.Action =
                NotificationCompat.Action.Builder(0, "Completed", pendingIntent1).build()

            val notification = p0?.let {
                NotificationCompat.Builder(it, "Click_Up")
                    .setContentTitle("Task Reminder")
                    .setContentText(taskInfo?.description)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .addAction(action1)
                    .build()
            }
            notificationManager = p0?.let { NotificationManagerCompat.from(it) }
            notification?.let { taskInfo?.let { it1 -> notificationManager?.notify(it1.id,it) } }
        }
    }
}