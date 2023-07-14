package com.example.musicplayerdemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.reflect.KClass

class MusicPlayerService : Service() {

    var mBinder: MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder: Binder() {

        fun getService(): MusicPlayerService {
            return this@MusicPlayerService
        }

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        return mBinder
    }
}