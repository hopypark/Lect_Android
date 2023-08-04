package com.example.notificationdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput

class ReplyReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val replyText = intent?.let { RemoteInput.getResultsFromIntent(it)?.getCharSequence("key_text_reply") }
        Log.d("Reply", "Msg: "+ replyText)
    }
}