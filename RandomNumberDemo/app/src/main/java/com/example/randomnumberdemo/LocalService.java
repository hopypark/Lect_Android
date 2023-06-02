package com.example.randomnumberdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class LocalService extends Service {
    private static final String TAG = "LocalService";
    private final IBinder mBinder = new LocalBinder();

    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    public int getRandomNumber(){
        Random random = new Random(); // Seed - 시간 정보를 바탕으로 생성
        return random.nextInt(100);
    }

    class LocalBinder extends Binder{
        LocalService getService(){
            Log.d(TAG, "getService() 호출 - 서비스 객체 반환");
            return LocalService.this;
        }
    }
}

