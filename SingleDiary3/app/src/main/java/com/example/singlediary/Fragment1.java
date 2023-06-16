package com.example.singlediary;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class Fragment1 extends Fragment {
    final static String TAG = "SingleDiary";

    RecyclerView recyclerView;
    NoteAdapter adapter;
    Context context;
    OnTabItemSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        initUI(rootView);
//        Log.d(TAG, "onCreateView() - Fragment1");
        loadNoteListData();
        return rootView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private int loadNoteListData() {
        AppConstants.println("loadNoteListData called.");
        String sql = "selectr _id, WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, CONTENTS, MOOD, PICTURE, CREATE_DATE, MODIFY_DATE from "
                + NoteDatabase.TABLE_NOTE + " order by CREATE_DATE desc";

        int recordCount = -1;
        NoteDatabase database = NoteDatabase.getInstance(context);
        if (database != null){
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            AppConstants.println("record count: " + recordCount + "\n");

            ArrayList<Note> items = new ArrayList<Note>();
            for(int i = 0 ; i < recordCount ; i++){
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String weather = outCursor.getString(1);
                String address = outCursor.getString(2);
                String locationX = outCursor.getString(3);
                String locationY = outCursor.getString(4);
                String contents = outCursor.getString(5);
                String mood = outCursor.getString(6);
                String picture = outCursor.getString(7);
                String dateStr = outCursor.getString(8);
                String createDateStr = null;
                if (dateStr != null && dateStr.length() > 10){
                    try {
                        Date inDate = AppConstants.dateFormat4.parse(dateStr);
                        createDateStr = AppConstants.dateFormat3.format(inDate);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    createDateStr = "";
                }
                AppConstants.println("#" + i + " -> " + _id + ", " + weather + ", "
                        + address + ", " + locationX + ", " + locationY + ", " + contents + ", "
                        + mood + ", " + picture + ", " + createDateStr);
                items.add(new Note(_id, weather, address, locationX, locationY, contents, mood, picture, createDateStr));
            }
            outCursor.close();
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
        return recordCount;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof OnTabItemSelectedListener){
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (context != null){
            context = null;
            listener = null;
        }
    }

    private void initUI(ViewGroup rootView) {
        Button todayWriteButton = rootView.findViewById(R.id.todayWriteButton);
        todayWriteButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onTabSelected(1);
                }
            }
        });

        SwitchCompat switchCompat = (SwitchCompat) rootView.findViewById(R.id.switch_btn);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(), "내용", Toast.LENGTH_SHORT).show();
                    adapter.switchLayout(0);
                }else{
                    Toast.makeText(getContext(), "사진", Toast.LENGTH_SHORT).show();
                    adapter.switchLayout(1);
                }
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Log.d(TAG, "Context: " + getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter();
        adapter.addItem(new Note(0, "0", "강남구 삼성동", "", "",
                "오늘 너무 행복해!", "0", "capture1.jpg","2월10일"));
        adapter.addItem(new Note(1, "1", "강남구 삼성동", "", "",
                "친구와 재미있게 놀았어", "0", "capture1.jpg","2월11일"));
        adapter.addItem(new Note(2, "2", "강남구 역삼동", "", "",
                "집에 왔는데 너무 피곤해 ㅠㅠ", "0", null,"2월13일"));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnNoteItemClickListener() {
            @Override
            public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position) {
                Note item = adapter.getItem(position);
                //Toast.makeText(getContext(), "아이템이 선택됨: " + item.getContents(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "아이템이 선택됨: " + item.getContents());
            }
        });
    }


}
