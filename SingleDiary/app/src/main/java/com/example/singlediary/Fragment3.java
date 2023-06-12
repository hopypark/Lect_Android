package com.example.singlediary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    final static String TAG = "SingleDiary";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container,false);
        initUI(rootView);
        Log.d(TAG, "onCreateView() - Fragment3");
        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initUI(ViewGroup rootView) {
    }
}
