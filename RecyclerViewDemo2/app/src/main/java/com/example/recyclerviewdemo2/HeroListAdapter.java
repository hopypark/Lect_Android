package com.example.recyclerviewdemo2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeroListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<String> heroList;

    public HeroListAdapter(List<String> heroList) {
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("HeroListAdapter","onCreateViewHolder()");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("HeroListAdapter","onBindViewHolder()");
        holder.tv_name.setText(heroList.get(position));
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
    }
}