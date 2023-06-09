package io.realworld.pushnotificationapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        button1.setBackgroundColor(Color.TRANSPARENT)
        button2.setBackgroundColor(Color.TRANSPARENT)
        button3.setBackgroundColor(Color.TRANSPARENT)

        val nm: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel = NotificationChannel("first", "default", NotificationManager.IMPORTANCE_HIGH)
            channel.apply{
                enableLights(true)
                enableVibration(true)
            }
            nm.createNotificationChannel(channel)
        }

        button1.setOnClickListener{
            val builder =
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    Notification.Builder(this, "first")
                }
                else{
                    Notification.Builder(this)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
                }
            val simpleNotification = builder
                .setContentTitle("Simple Title")
                .setContentText("This is sample description of the notification")
                .setSmallIcon(R.drawable.notification_icon)
                .build()
            nm.notify(1,simpleNotification)
        }

        button2.setOnClickListener{
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://www.google.com")
            val pi = PendingIntent.getActivity(getApplicationContext(),123, i , PendingIntent.FLAG_MUTABLE)
            val clickableNotification = NotificationCompat.Builder(this, "first")
                .setContentTitle("Clickable Title")
                .setContentIntent(pi)
                .setAutoCancel(true) // Auto removes the notification after clicking
                .setContentText("This is sample description of the notification")
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            nm.notify(2,clickableNotification)
        }

        button3.setOnClickListener{
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://www.google.com")
            val pi = PendingIntent.getActivity(getApplicationContext(),123, i , PendingIntent.FLAG_MUTABLE)
            val clickableNotification = NotificationCompat.Builder(this, "first")
                .setContentTitle("Clickable with Button Title")
                .setSmallIcon(R.drawable.notification_icon)
                .addAction(R.drawable.notification_icon, "Click Me", pi)
                .setContentIntent(pi)
                .setAutoCancel(true) // Auto removes the notification after clicking
                .setContentText("This is sample description of the notification")

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            nm.notify(3,clickableNotification)
        }

    }
}