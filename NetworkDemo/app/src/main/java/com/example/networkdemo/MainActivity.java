package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
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


    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network activeNetwoprk = connMgr.getActiveNetwork(); // 현재 활동중인 네트워크 가져오기
            NetworkCapabilities capabilities = connMgr.getNetworkCapabilities(activeNetwoprk);
            if (capabilities != null){
              if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                  display.setText("WI-FI 동작 중");
              } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                  display.setText("CELLULAR 동작 중");
              }
             }else {
                display.setText("동작 중인 WI-FI나 CELLULAR가 없음");
            }
        } else {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null){
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                    display.setText("WI-FI 동작 중");
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    display.setText("CELLULAR 동작 중");
                }
            }else {
                display.setText("동작 중인 WI-FI나 CELLULAR가 없음");
            }
        }
    } // checkConnection() 끝

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            checkConnection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }
}

