package com.example.singlediary;

import android.content.Context;
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
        Log.d(TAG, "onCreateView() - Fragment1");
        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
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
