package com.example.bai3_alarmclock

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.aaa)
        mediaPlayer.isLooping = true
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ccc","service")
        // Khởi tạo MediaPlayer và phát chuông



        // Tạo Intent và PendingIntent để dừng báo thức

        // Tạo Notification Channel
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("ALARM_CHANNEL", "Báo Thức", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val stopIntent = Intent(this, AlarmService::class.java).apply {
            action = "STOP_ALARM"
        }
        val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Tạo Notification với nút hành động để tắt báo thức
        val notification = NotificationCompat.Builder(this, "ALARM_CHANNEL")
            .setContentTitle("Báo Thức")
            .setContentText("Báo thức đang đổ chuông")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_launcher_foreground, "Tắt", pendingStopIntent)
            .setAutoCancel(true)
            .build()


        startForeground(1, notification)
        mediaPlayer.start()

        if (intent?.action == "STOP_ALARM") {
            Log.d("ccc", "Nhận lệnh tắt báo thức")
                mediaPlayer.pause()
            mediaPlayer.stop()
            mediaPlayer.release()
                Log.d("ccc", "Nhạc dừng")

            // Dừng service
            stopSelf()
            Log.d("eee", "Click dừng")

        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()

        Log.d("ccc", "service destroyed")

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}