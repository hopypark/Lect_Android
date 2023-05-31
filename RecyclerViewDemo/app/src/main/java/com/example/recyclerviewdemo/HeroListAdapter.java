package com.example.recyclerviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeroListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<String> heroList; // MainActivity에서 데이터를 받음.

    public HeroListAdapter(List<String> heroList){
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("HeroListAdapter","onCreateViewHolder()");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hero_layout, parent, false);
        return new MyViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("HeroListAdapter","onBindViewHolder()");
        holder.tv_heroName.setText(heroList.get(position));
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv_heroName;
    Button btn_HeroName;

    public MyViewHolder(Context mContext, @NonNull View itemView) {
        super(itemView);
        tv_heroName = itemView.findViewById(R.id.tv_heroName); // hero_layout에서의 tv_heroName
        btn_HeroName = itemView.findViewById(R.id.btn_HeroName);
        btn_HeroName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Hello "+tv_heroName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

