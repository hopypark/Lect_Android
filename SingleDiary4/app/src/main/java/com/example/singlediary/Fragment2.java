package com.example.singlediary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.github.channguyen.rsv.RangeSliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment2 extends Fragment {
    final static String TAG = "Fragment2";

    Context context;
    OnTabItemSelectedListener listener;
    OnRequestListener requestListener;

    ImageView weatherIcon;
    TextView dateTextView;
    TextView locationTextView;

    EditText contentsInput;
    ImageView pictureImageView;

    boolean isPhotoCaptured;
    boolean isPhotoFileSaved;
    boolean isPhotoCanceled;

    int selectedPhotoMenu;

    Uri uri;
    File file;
    Bitmap resultPhotoBitmap;
    //
    int mMode = AppConstants.MODE_INSERT;
    int _id = -1;
    int weatherIndex = 0;

    RangeSliderView moodSlider;
    int moodIndex = 2;
    Note item;
    //
    SimpleDateFormat todayDateFormat;
    String currentDateString;

    ActivityResultLauncher<Intent> photoSelectionlauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                Log.d(TAG, "onActivityResult() for REQ_PHOTO_SELECTION.");
                Intent intent = result.getData();
                Uri fileUri = intent.getData();
                //
                ContentResolver resolver = context.getContentResolver();
                try{
                    InputStream inStream = resolver.openInputStream(fileUri);
                    resultPhotoBitmap = BitmapFactory.decodeStream(inStream);
                    pictureImageView.setImageBitmap(resultPhotoBitmap);
                    inStream.close();
                    isPhotoCaptured = true;
                }catch (Exception e){e.printStackTrace();}
            }
        }
    });

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
  //          if(result.getResultCode() == AppConstants.REQ_PHOTO_CAPTURE) {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                Log.d(TAG, "onActivityResult() for REQ_PHOTO_CAPTURE.");
                resultPhotoBitmap = decodeSampledBitmapFromResource(file,
                        pictureImageView.getWidth(), pictureImageView.getHeight());
                pictureImageView.setImageBitmap(resultPhotoBitmap);
            }
        }
    });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof OnTabItemSelectedListener){
            listener = (OnTabItemSelectedListener) context;
        }

        if (context instanceof  OnRequestListener){
            requestListener = (OnRequestListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(context != null){
            context = null;
            listener = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        initUI(rootView);
        // 입력화면이 보일 때마다 현재 위치를 파악할 수 있도록 MainActivity의 onRequyest()메서드가 호출
        if(requestListener != null){
            requestListener.onRequest("getCurrentLocation");
        }
        Log.d(TAG, "onCreateView()");
        applyItem();
        
        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void applyItem() {
        AppConstants.println("applyItem called.");
        if(item != null){
            mMode = AppConstants.MODE_MODIFY;
            
            setWeatherIndex(Integer.parseInt(item.getWeather()));
            setAddress(item.getAddress());
            setDateString(item.getCreateDateStr());
            setContents(item.getContents());

            String picturePath = item.getPicture();
            AppConstants.println("picturePath: " + picturePath);

            if(picturePath == null || picturePath.equals("")){
                pictureImageView.setImageResource(R.drawable.noimagefound);
            } else {
                setPicture(item.getPicture(), 1);
            }
            setMood(item.getMood());
        } else {
            mMode = AppConstants.MODE_INSERT;

            setWeatherIndex(0);
            setAddress("");
            Date currentDate = new Date();
            if(todayDateFormat == null){
                todayDateFormat = new SimpleDateFormat(getResources().getString(R.string.today_date_format));
            }
            currentDateString = todayDateFormat.format(currentDate);
            AppConstants.println("currentDateString: " + currentDateString);
            setDateString(currentDateString);

            contentsInput.setText("");
            pictureImageView.setImageResource(R.drawable.noimagefound);
            setMood("2");
        }
    }

    public void setItem(Note item){
        this.item = item;
    }

    private void setMood(String mood) {
        try{
            moodIndex = Integer.parseInt(mood);
            moodSlider.setInitialIndex(moodIndex);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setPicture(String picturePath, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        resultPhotoBitmap = BitmapFactory.decodeFile(picturePath, options);

        pictureImageView.setImageBitmap(resultPhotoBitmap);
    }

    private void setContents(String contents) {
        contentsInput.setText(contents);
    }

    private void setWeatherIndex(int parseInt) {
        switch (parseInt){
            case 0:
                weatherIcon.setImageResource(R.drawable.weather_1);
                weatherIndex = 0;
                break;
            case 1:
                weatherIcon.setImageResource(R.drawable.weather_2);
                weatherIndex = 1;
                break;
            case 2:
                weatherIcon.setImageResource(R.drawable.weather_3);
                weatherIndex = 2;
                break;
            case 3:
                weatherIcon.setImageResource(R.drawable.weather_4);
                weatherIndex = 3;
                break;
            case 4:
                weatherIcon.setImageResource(R.drawable.weather_5);
                weatherIndex = 4;
                break;
            case 5:
                weatherIcon.setImageResource(R.drawable.weather_6);
                weatherIndex = 5;
                break;
            case 6:
                weatherIcon.setImageResource(R.drawable.weather_7);
                weatherIndex = 6;
                break;
            default:
                Log.d(TAG, "Unknown weather index: " + parseInt);
                break;

        }
    }


    private void initUI(ViewGroup rootView) {
        weatherIcon = rootView.findViewById(R.id.weatherIcon);
        dateTextView = rootView.findViewById(R.id.dateTextView);
        locationTextView = rootView.findViewById(R.id.locationTextView);

        contentsInput = rootView.findViewById(R.id.contentsInput);
        pictureImageView = rootView.findViewById(R.id.pictureImageView);
//        pictureImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPhotoCaptured || isPhotoFileSaved){
//                    showDialog(AppConstants.CURRENT_PHOTO_EX);
//                } else {
//                    showDialog(AppConstants.CURRENT_PHOTO);
//                }
//            }
//        });
        pictureImageView.setOnClickListener(
                it -> {
                    if(isPhotoCaptured || isPhotoFileSaved){
                        showDialog(AppConstants.CURRENT_PHOTO_EX);
                    } else {
                        showDialog(AppConstants.CURRENT_PHOTO);
                    }
                }
        );

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(it->{
                if (mMode == AppConstants.MODE_INSERT){
                    saveNote();
                } else if (mMode == AppConstants.MODE_MODIFY) {
                    modifyNote();
                }
                if(listener != null) listener.onTabSelected(0);

        });
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mMode == AppConstants.MODE_INSERT){
//                    saveNote();
//                } else if (mMode == AppConstants.MODE_MODIFY) {
//                    modifyNote();
//                }
//                if(listener != null) listener.onTabSelected(0);
//            }
//        });

        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
                if (listener != null) listener.onTabSelected(0);
            }
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onTabSelected(0);
            }
        });

        // RangeSliderView
        moodSlider = rootView.findViewById(R.id.slideView);
        moodSlider.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {

               // Toast.makeText(context, "moodIndex changed to " + index, Toast.LENGTH_SHORT).show();
                AppConstants.println("moodIndex changed to " + index);
                moodIndex = index;
            }
        });
//        moodSlider.setOnSlideListener(listener);
        moodSlider.setInitialIndex(2);
    }

    private void deleteNote() {
        AppConstants.println("deleteNote called.");

        if (item != null){
            String sql = "delete from " + NoteDatabase.TABLE_NOTE +
                    " where _id = " + item._id;
            Log.d(TAG, "sql: " + sql);
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    private void modifyNote() {
        if(item != null){
            String address = locationTextView.getText().toString();
            String contents = contentsInput.getText().toString();

            String picturePath = savePicture();
            String sql = "update " + NoteDatabase.TABLE_NOTE +
                    " set " +
                    " WEATHER = '" + weatherIndex +"', " +
                    " ADDRESS = '" + address +"', " +
                    " LOCATION_X = '" + "" + "', " +
                    " LOCATION_Y = '" + "" + "', " +
                    " CONTENTS = '" + contents + "', " +
                    " MOOD = '" + moodIndex +"', " +
                    " PICTURE = '" + picturePath +"' " +
                    "where _id = " + item._id;
            Log.d(TAG, "sql: " + sql);
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    private void saveNote() {
            String address = locationTextView.getText().toString();
            String contents = contentsInput.getText().toString();
            String picturePath = savePicture();

            String sql = "insert into " + NoteDatabase.TABLE_NOTE +
                    "(WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, CONTENTS, MOOD, PICTURE) values("+
                    "'" + weatherIndex +"', " +
                    "'" + address +"', " +
                    "'" + "', " +
                    "'" + "', " +
                    "'" + contents + "', " +
                    "'" + moodIndex + "', " +
                    "'" + picturePath +"')";
            Log.d(TAG, "sql: " + sql);
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(sql);

    }

    private String savePicture() {
        if(resultPhotoBitmap == null){
            AppConstants.println("No picuture to be saved.");
            return "";
        }

        File photoFolder = new File(AppConstants.FOLDER_PHOTO);

        if(!photoFolder.isDirectory()){
            Log.d(TAG, "creating photo folder: " + photoFolder);
            photoFolder.mkdirs();
        }
        String photoFilename = createFilename();
        String picturePath = photoFolder + File.separator + photoFilename;

        try {
            FileOutputStream outputStream = new FileOutputStream(picturePath);
            resultPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return picturePath;
    }

    private void showDialog(int id) {
        AlertDialog.Builder builder = null;

        switch (id){
            case AppConstants.CURRENT_PHOTO:
                builder = new AlertDialog.Builder(context);
                builder.setTitle("사진 메뉴 선택");
                builder.setSingleChoiceItems(R.array.array_photo, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPhotoMenu = which;
                    }
                });
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedPhotoMenu == 0){
                            showPhotoCaptureActivity();
                        } else if (selectedPhotoMenu == 1) {
                            showPhotoSelectionActivity();
                        }
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case AppConstants.CURRENT_PHOTO_EX:
                builder = new AlertDialog.Builder(context);
                builder.setTitle("사진 메뉴 선택");

                builder.setSingleChoiceItems(R.array.array_photo_ex, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPhotoMenu = which;
                    }
                });
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(selectedPhotoMenu == 0){
                            showPhotoCaptureActivity();
                        } else if (selectedPhotoMenu == 1){
                            showPhotoSelectionActivity();
                        } else if (selectedPhotoMenu == 2){
                            isPhotoCanceled = true;
                            isPhotoCaptured = false;

                            pictureImageView.setImageResource(R.drawable.picture1);
                        }
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            default:
                break;
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPhotoSelectionActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

//        startActivityForResult(intent, AppConstants.REQ_PHOTO_SELECTION);
        photoSelectionlauncher.launch(intent);
    }


    private void showPhotoCaptureActivity() {
        try{
            file = createFile();
            if (file.exists()) { file.delete(); }
            file.createNewFile();
        }catch (Exception e){e.printStackTrace();}

        if(Build.VERSION.SDK_INT >= 24){
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(intent, AppConstants.REQ_PHOTO_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private File createFile() {
        String filename = createFilename();
        File outFile = new File(context.getFilesDir(), filename);
        Log.d("Main", "File path: " + outFile.getAbsolutePath());
        return outFile;
    }

    private String createFilename() {
        Date currDate = new Date();
        String currDateStr = String.valueOf(currDate.getTime());
        return currDateStr;
    }

    public static Bitmap decodeSampledBitmapFromResource(File res, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res.getAbsolutePath(), options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(res.getAbsolutePath(), options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth){
            final int halfHeight = height;
            final int halfWidth = width;
            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public void setDateString(String currentDateString) {
        dateTextView.setText(currentDateString);
    }

    public void setAddress(String currentAddress) {
        locationTextView.setText(currentAddress);
    }


    public void setWeather(String data) {
        if(data != null){
            if(data.equals("맑음")){
                weatherIcon.setImageResource(R.drawable.weather_1);
            } else if (data.equals("구름 조금")) {
                weatherIcon.setImageResource(R.drawable.weather_2);
            } else if (data.equals("구름 많음")) {
                weatherIcon.setImageResource(R.drawable.weather_3);
            } else if (data.equals("흐림")) {
                weatherIcon.setImageResource(R.drawable.weather_4);
            } else if (data.equals("비")) {
                weatherIcon.setImageResource(R.drawable.weather_5);
            } else if (data.equals("눈/비")) {
                weatherIcon.setImageResource(R.drawable.weather_6);
            } else {
                weatherIcon.setImageResource(R.drawable.weather_7);
            }
        }
    }
}
