package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        msg()
    }

    fun msg(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            // 채널에 다양한 정보 설정
            channel.description = "My Channel One Description"
            channel.setShowBadge(true)
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            channel.setSound(uri, audioAttributes)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100,200,100,200)
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)
            // 채널을 이용해 빌더 생성
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("Content Title")
        builder.setContentText("Content Message")
        /// 이미지 스타일
//        val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.banff1)
//        val bigStyle = NotificationCompat.BigPictureStyle()
//        bigStyle.bigPicture(bigPicture)
//        builder.setStyle(bigStyle)

        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.bigText(resources.getString(R.string.poem))
        builder.setStyle(bigTextStyle)

        // Action
//        val actionIntent = Intent(this, OneReceiver::class.java)
//        val actionPendingIntent = PendingIntent.getBroadcast(this, 20, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        builder.addAction(NotificationCompat.Action.Builder(
//            android.R.drawable.stat_notify_more, "Action", actionPendingIntent
//        ).build())
//        // 원격 입력
//        val KEY_TEXT_REPLY = "key_text_reply"
//        var replyLabel: String = "답장"
//        var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
//                                            .setLabel(replyLabel)
//                                            .build()
//
//        val replyIntent = Intent(this, ReplyReceiver::class.java)
//        val replyPendingIntent = PendingIntent.getBroadcast(this, 30, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        builder.addAction(
//            NotificationCompat.Action.Builder(
//                R.drawable.send,
//                "답장",
//                replyPendingIntent
//            ).addRemoteInput(remoteInput).build()
//        )

        manager.notify(11, builder.build())
    }
}