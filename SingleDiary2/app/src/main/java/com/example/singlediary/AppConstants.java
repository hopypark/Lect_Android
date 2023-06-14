package com.example.singlediary;

import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AppConstants {
    public static final int REQ_LOCATION_BY_ACCESS = 101;
    public static final int REQ_WEATHER_BY_GRID = 102;

    public static final int REQ_PHOTO_CAPTURE = 103;
    public static final int REQ_PHOTO_SELECTION = 104;

    public static final int CURRENT_PHOTO = 105;
    public static final int CURRENT_PHOTO_EX = 106;

    public static String FOLDER_PHOTO;
    public static final String KEY_URI_PHOTO = "URI_PHOTO";

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("YYYY-MM-dd HH시");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM월 dd일");

//    private static Handler handler = new Handler(Looper.getMainLooper()).postDelayed(new Run)

//    private static final String TAG = "AppConstants";
//    public static void println(final String data){
//        handler.post(new Runnable(
//
//        ));
//    }

}
