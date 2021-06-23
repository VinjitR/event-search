package com.example.eventsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class tickResults extends AppCompatActivity {

    private List<Search> searchList;

    private RecyclerView recyclerView;
    private TextView noSearchResult;
    boolean noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tick_results);
        recyclerView = findViewById(R.id.eventTable);
        noSearchResult = findViewById(R.id.noSearchResult);
        noResult = false;

        searchList = new ArrayList<>();


        String resultTableString = getIntent().getExtras().getString("resultTableString");
        try {
            JSONObject resultTableJSON = new JSONObject(resultTableString);
            JSONArray events = resultTableJSON.getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject singleEvent = events.getJSONObject(i);
                Search anime = new Search();

                anime.setName(singleEvent.getString("name"));

                anime.setTicketMasterURL(singleEvent.getString("url"));

                anime.setEventID(singleEvent.getString("id"));

                String category = singleEvent.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
                anime.setClassifications(category);

                anime.setLocalDate(singleEvent.getJSONObject("dates").getJSONObject("start").getString("localDate"));
                anime.setLocalTime(singleEvent.getJSONObject("dates").getJSONObject("start").getString("localTime"));

                anime.setVenue(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));

                anime.setLat(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("latitude"));

                anime.setLng(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("longitude"));

                String categoryIconURL;
                if (category.equals("Music")) {
                    categoryIconURL = "http://csci571.com/hw/hw9/images/android/music_icon.png";
                } else if (category.equals("Sports")) {
                    categoryIconURL = "http://csci571.com/hw/hw9/images/android/sport_icon.png";
                } else if (category.equals("Miscellaneous")) {
                    categoryIconURL = "http://csci571.com/hw/hw9/images/android/miscellaneous_icon.png";
                } else if (category.equals("Film")) {
                    categoryIconURL = "http://csci571.com/hw/hw9/images/android/film_icon.png";
                } else {
                    categoryIconURL = "http://csci571.com/hw/hw9/images/android/art_icon.png";
                }

                anime.setImageURL(categoryIconURL);

                animeList.add(anime);

                Log.i("Events", events.getJSONObject(i).toString());
            }
        } catch (JSONException | JSONException e) {
            noResult = true;
            Log.i("ERROR", "No search result");
        }

        setRecyclerView(tickResults);
    }
    }
}