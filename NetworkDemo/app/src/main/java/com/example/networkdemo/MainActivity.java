package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView display;

    WifiReceiver wifiReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.tv_info);
        wifiReceiver = new WifiReceiver();

    }   // onCreate() 끝

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceiver, filter);
    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo(); // 현재 활동중인 네트워크 가져오기
            boolean isConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
            if (isConnected) {
                display.setText("CELLULAR: " + (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                        + ", WIFI: " + (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI));
            }else {
                display.setText("연결된 네트워크가 없다.");
            }
        }
    };

}

