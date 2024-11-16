package com.example.bai3_alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var timePicker: TimePicker
    private lateinit var checkBoxRepeat: CheckBox
    private lateinit var editTextTime: EditText
    private lateinit var buttonSetAlarm: Button
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        timePicker = findViewById(R.id.timePicker)
        checkBoxRepeat = findViewById(R.id.checkBoxRepeat)
        editTextTime = findViewById(R.id.editTextTime)
        buttonSetAlarm = findViewById(R.id.buttonSetAlarm)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        buttonSetAlarm.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
                set(Calendar.SECOND, 0)
            }


            editTextTime.setText(String.format("%02d:%02d", timePicker.hour, timePicker.minute))


            val intent = Intent(this, AlarmReceiver::class.java)
           // pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            pendingIntent = PendingIntent.getBroadcast(this,1,intent,0)

            if (checkBoxRepeat.isChecked) {

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis-(calendar.getTimeInMillis() % 60000),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
                Log.d("aaa","Lặp lại")
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,0,
                    pendingIntent
                )
                Log.d("aaa","ko lặp lại")
            }
        }
    }
}