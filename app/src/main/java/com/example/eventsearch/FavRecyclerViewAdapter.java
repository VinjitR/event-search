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


public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavRecyclerViewAdapter.FavViewHolder> {

    private Context mContext;
    private List<faveD> mFavData;
    View view;

    public FavRecyclerViewAdapter(Context mContext, List<faveD> mFavData) {
        this.mContext = mContext;
        this.mFavData = mFavData;

    }


    @NonNull
    @Override
    public FavRecyclerViewAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.favorite_row, parent, false);


        return new FavRecyclerViewAdapter.FavViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavRecyclerViewAdapter.FavViewHolder favViewHolder, int position) {
        if(mFavData.size()>0){
        final faveD faveDd = mFavData.get(position);
        favViewHolder.faveventName.setText(faveDd.getFavevent());
        favViewHolder.favvenueName.setText(faveDd.getFavvenue());

        String eventLocal_date_time = faveDd.getFavdatetime();
        favViewHolder.faveventTime.setText(eventLocal_date_time);

        favViewHolder.faveventID.setText(faveDd.getFavid());
        String Lats=faveDd.getFavlatlng();

        favViewHolder.faveventLatLng.setText(faveDd.getFavlatlng());

        favViewHolder.faveventCategory_thumbnail.setImageResource(faveDd.getFavcatImage());


        favoriteButton(faveDd.getFavevent(), faveDd.getFavid(),faveDd.getFavvenue(),faveDd.getFavdatetime(),faveDd.getFavlatlng(),faveDd.getFavcatImage(), position);

        favViewHolder.favlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, detailActivity.class);
                intent.putExtra("eventName", faveDd.getFavevent());
                intent.putExtra("eventID", faveDd.getFavid());
                intent.putExtra("eventVenue", faveDd.getFavvenue());
                intent.putExtra("eventTime",faveDd.getFavdatetime());
                //Log.i("lat",fa.getLatlng());
                intent.putExtra("eveLatlng",faveDd.getFavlatlng());
                intent.putExtra("eveCatImg",""+faveDd.getFavcatImage());
                mContext.startActivity(intent);
            }
        });}

    }

    private void favoriteButton(String eventName, String eventID,String eventVenue,String eventDatetime,String eventLatlng,int evecatImage, int position) {
        Button favoriteButton = view.findViewById(R.id.favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("favorite", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String sharedPreferencesName = eventID;

                Toast.makeText(view.getContext(), eventName + " was removed from favorites", Toast.LENGTH_SHORT).show();
                editor.remove(sharedPreferencesName);
                editor.commit();
                removeFav(position);
            }
        });
    }

    public void removeFav(int position) {
        Log.i("pop",""+position);
        if(mFavData.size()>=position&&mFavData.size()>0){
        mFavData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mFavData.size());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("favorite", Context.MODE_PRIVATE);
    }
    }


    @Override
    public int getItemCount() {
        return mFavData.size();
    }


    public static class FavViewHolder extends RecyclerView.ViewHolder {
        TextView faveventName;
        TextView favvenueName;
        TextView faveventTime;
        ImageView faveventCategory_thumbnail;
        TextView faveventID;
        TextView faveventLatLng;

        LinearLayout favlinearLayout;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            faveventName = itemView.findViewById(R.id.favename);
            favvenueName = itemView.findViewById(R.id.favevenue);
            faveventTime = itemView.findViewById(R.id.favetime);
            faveventCategory_thumbnail = itemView.findViewById(R.id.favcatthumbnail);
            faveventID = itemView.findViewById(R.id.faveID);
            faveventLatLng=itemView.findViewById(R.id.faveLatLng);
            favlinearLayout = itemView.findViewById(R.id.favlayot);

        }


    }
}