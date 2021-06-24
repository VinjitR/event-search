package com.example.eventsearch;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<eventD> mEventData;
;
    View view;

    public RecyclerViewAdapter(Context mContext, List<eventD> mEventData) {
        this.mContext = mContext;
        this.mEventData = mEventData;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_search_result_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        final eventD eventeventD = mEventData.get(position);

        //better name should be coded
        myViewHolder.eventName.setText(eventeventD.getEvent());
        myViewHolder.venueName.setText(eventeventD.getVenue());

        String eventLocal_date_time = eventeventD.getDatetime();
        myViewHolder.eventTime.setText(eventLocal_date_time);

        myViewHolder.eventID.setText(eventeventD.getId());
        myViewHolder.eventCategory_thumbnail.setImageResource(eventeventD.getCatImage());
//        myViewHolder.eventLatitude.setText(eventeventD.getLat());
//        myViewHolder.eventLongitude.setText(eventeventD.getLng());

        // load image from the internet and set it into Imageview using Glide
        //Glide.with(mContext).load(eventeventD.getImageURL()).apply(option).into(myViewHolder.eventCategory_thumbnail);

        // if an event is already in favorite, make the favorite button red
        Button favoriteButton = view.findViewById(R.id.favorite);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("favorite", Context.MODE_PRIVATE);

        String sharedPreferencesName = eventeventD.getEvent() + eventeventD.getId();
        if (sharedPreferences.contains(sharedPreferencesName)) {
            favoriteButton.setBackgroundResource(R.drawable.heart_fill_red);
        } else {
            favoriteButton.setBackgroundResource(R.drawable.heart_outline_black);
        }


        favoriteButton(eventeventD.getEvent(), eventeventD.getId());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, detailActivity.class);
                intent.putExtra("eventName", eventeventD.getEvent());
                intent.putExtra("eventID", eventeventD.getId());
                intent.putExtra("eventVenue", eventeventD.getVenue());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEventData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView venueName;
        TextView eventTime;
        ImageView eventCategory_thumbnail;
        TextView eventID;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            venueName = itemView.findViewById(R.id.venue);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventCategory_thumbnail = itemView.findViewById(R.id.catthumbnail);
            eventID = itemView.findViewById(R.id.eventID);
            linearLayout = itemView.findViewById(R.id.eventsLinearLayout);
        }
    }

    public void favoriteButton(String eventName, String eventID) {
        Button favoriteButton = view.findViewById(R.id.favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("favorite", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String sharedPreferencesName = eventName + eventID;

                if (sharedPreferences.contains(sharedPreferencesName)) {
                    Toast.makeText(view.getContext(), eventName + " was removed from favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setBackgroundResource(R.drawable.heart_outline_black);
                    editor.remove(sharedPreferencesName);
                    editor.commit();
                } else {
                    Toast.makeText(view.getContext(), eventName + " was added to favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setBackgroundResource(R.drawable.heart_fill_red);
                    editor.putString(sharedPreferencesName, eventID);
                    editor.apply();
                }
            }
        });
    }
}