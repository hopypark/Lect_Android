package com.example.singlediary;

import android.media.TimedText;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements OnNoteItemClickListener{
    final static String TAG = "SingleDiary";
    
    ArrayList<Note> items = new ArrayList<Note>();
    OnNoteItemClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
       Note item = items.get(position);
       holder.setItem(item);
       holder.setLayoutType(layoutType);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount(): " + items.size());
        return items.size();
    }

    public void addItem(Note item){
        Log.d(TAG, "addItem(): " + item);
        items.add(item);
    }

    public void setItems(ArrayList<Note> items){
        this.items = items;
    }

    public Note getItem(int position){
        Log.d(TAG, "getItem(): " + items.get(position));
        return items.get(position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public void switchLayout(int position){
        layoutType = position;
    }

    public void setOnItemClickListener(OnNoteItemClickListener listener){
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout1, layout2;

        ImageView moodImageView, moodImageView2;
        ImageView pictureExistsImageView, pictureImageView;
        ImageView weatherImageView, weatherImageView2;
        TextView contentsTextView, contentsTextView2;
        TextView locationTextView, locationTextView2;
        TextView dateTextView, dataTextView2;

        public ViewHolder(@NonNull View itemView, final OnNoteItemClickListener listener, int layoutType) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);
            layout2 = itemView.findViewById(R.id.layout2);

            moodImageView = itemView.findViewById(R.id.moodImageView);
            moodImageView2 = itemView.findViewById(R.id.moodImageView2);

            pictureExistsImageView = itemView.findViewById(R.id.pictureExistsImageView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);

            weatherImageView = itemView.findViewById(R.id.weatherImageView);
            weatherImageView2 = itemView.findViewById(R.id.weatherImageView2);

            contentsTextView = itemView.findViewById(R.id.contentsTextView);
            contentsTextView2 = itemView.findViewById(R.id.contentsTextView2);

            locationTextView = itemView.findViewById(R.id.locationTextView);
            locationTextView2 = itemView.findViewById(R.id.locationTextView2);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            dataTextView2 = itemView.findViewById(R.id.dateTextView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
            setLayoutType(layoutType);
        }

        public void setItem(Note item){
            String mood = item.getMood();
            int moodIndex = Integer.parseInt(mood);
            setMoodImage(moodIndex);

            String picturePath = item.getPicture();
            if (picturePath != null && !picturePath.equals("")){
                pictureExistsImageView .setVisibility(View.VISIBLE);
                pictureImageView.setVisibility(View.VISIBLE);
                pictureImageView.setImageURI(Uri.parse("file://" + picturePath));
            } else{
                pictureExistsImageView.setVisibility(View.GONE);
                pictureImageView.setVisibility(View.GONE);
                pictureImageView.setImageResource(R.drawable.noimagefound);
            }

            String weather = item.getWeather();
            int weatherIndex = Integer.parseInt(weather);
            setWeatherImage(weatherIndex);

            contentsTextView.setText(item.getContents());
            contentsTextView2.setText(item.getContents());

            locationTextView.setText(item.getAddress());
            locationTextView2.setText(item.getAddress());

            dateTextView.setText(item.getCreateDateStr());
            dataTextView2.setText(item.getCreateDateStr());
            Log.d(TAG, "setItem() 메서드 호출");
        }

        public void setWeatherImage(int weatherIndex) {
            switch (weatherIndex){
                case 0:
                    weatherImageView.setImageResource(R.drawable.weather_icon_1);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_1);
                    break;
                case 1:
                    weatherImageView.setImageResource(R.drawable.weather_icon_2);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_2);
                    break;
                case 2:
                    weatherImageView.setImageResource(R.drawable.weather_icon_3);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_3);
                    break;
                case 3:
                    weatherImageView.setImageResource(R.drawable.weather_icon_4);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_4);
                    break;
                case 4:
                    weatherImageView.setImageResource(R.drawable.weather_icon_5);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_5);
                    break;
                case 5:
                    weatherImageView.setImageResource(R.drawable.weather_icon_6);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_6);
                    break;
                case 6:
                    weatherImageView.setImageResource(R.drawable.weather_icon_7);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_7);
                    break;
                default:
                    weatherImageView.setImageResource(R.drawable.weather_icon_1);
                    weatherImageView2.setImageResource(R.drawable.weather_icon_1);
                    break;
            }
        }

        public void setMoodImage(int moodIndex) {
            switch (moodIndex){
                case 0:
                    moodImageView.setImageResource(R.drawable.smile1_48);
                    moodImageView2.setImageResource(R.drawable.smile1_48);
                    break;
                case 1:
                    moodImageView.setImageResource(R.drawable.smile2_48);
                    moodImageView2.setImageResource(R.drawable.smile2_48);
                    break;
                case 2:
                    moodImageView.setImageResource(R.drawable.smile3_48);
                    moodImageView2.setImageResource(R.drawable.smile3_48);
                    break;
                case 3:
                    moodImageView.setImageResource(R.drawable.smile4_48);
                    moodImageView2.setImageResource(R.drawable.smile4_48);
                    break;
                case 4:
                    moodImageView.setImageResource(R.drawable.smile5_48);
                    moodImageView2.setImageResource(R.drawable.smile5_48);
                    break;
                default:
                    moodImageView.setImageResource(R.drawable.smile3_48);
                    moodImageView2.setImageResource(R.drawable.smile3_48);
                    break;
            }
        }

        public void setLayoutType(int layoutType) {
            if (layoutType == 0){
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
            } else if (layoutType == 1){
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        }

    }
}
