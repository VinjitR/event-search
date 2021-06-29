package com.example.eventsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteTab extends Fragment {

    RecyclerView recyclerView;
    View view;
    private List<faveD> faveDList;




    @Override
    public void onResume() {
        super.onResume();
        getFav();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_favorite_tab, container, false);
        recyclerView=view.findViewById(R.id.favrecyleviewer);




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFav();
    }

    private void getFav() {
        faveDList=new ArrayList<>();
        SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("favorite", Context.MODE_PRIVATE);
        Map<String, ?> favKeys=sharedPreferences.getAll();

        if(favKeys.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            TextView noFavorite = view.findViewById(R.id.noFavorite);
            noFavorite.setVisibility(View.GONE);

            for (Map.Entry<String, ?> entry : favKeys.entrySet()) {
                    Log.i("favs",""+entry.getValue());
                    String fav;
                fav = ""+entry.getValue();
                String[] favParts;
                favParts = fav.split(";");
                Log.i("favparts",favParts[1]+favParts[2]+favParts[3]+favParts[4]);
                String eventID_Fav;
                eventID_Fav = favParts[0];
                String eventName_Fav;
                eventName_Fav = favParts[1];
                String eventVenue_Fav;
                eventVenue_Fav = favParts[2];
                String eventDatetime_Fav;
                eventDatetime_Fav = favParts[3];
                String eventLatlng_Fav;
                eventLatlng_Fav = favParts[4];
                int eventCatthumb_Fav;
                eventCatthumb_Fav = Integer.parseInt(favParts[5]);
                Log.i("catcheck",""+eventCatthumb_Fav);

                faveD favD=new faveD();
                favD.setFavid(eventID_Fav);
                favD.setFavevent(eventName_Fav);
                favD.setFavvenue(eventVenue_Fav);
                favD.setFavdatetime(eventDatetime_Fav);
                favD.setFavlatlng(eventLatlng_Fav);
                favD.setFavcatImage(eventCatthumb_Fav);


                faveDList.add(favD);



            }
            FavRecyclerViewAdapter favadapter = new FavRecyclerViewAdapter(view.getContext(), faveDList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(favadapter);


        }
        else{
            recyclerView.setVisibility(View.GONE);
            TextView noFav=view.findViewById(R.id.noFavorite);
            noFav.setVisibility(View.VISIBLE);


        }

    }

}