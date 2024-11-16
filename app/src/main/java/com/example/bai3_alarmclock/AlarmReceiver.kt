package com.example.bai3_alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Start the AlarmService để hiển thị Notification và phát chuông
        Log.d("bbb","receiver")
        val serviceIntent = Intent(context, AlarmService::class.java)
        context.startForegroundService(serviceIntent)
    }
}
