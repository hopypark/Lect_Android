package com.example.widgetdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;   //
    Integer[] mThumbIds;    // MainActivity에 선언되 Image의 ID값 받음

    public ImageAdapter(Context mContext, Integer[] mThumbIds){
        this.mContext = mContext;
        this.mThumbIds = mThumbIds;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        // 이미지 생성
        if (view == null){
            imageView = new ImageView(mContext); // 이미지뷰 생성
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);
        }else{
            imageView = (ImageView) view;
        }
        imageView.setImageResource(mThumbIds[i]);
        return imageView;
    }
}

