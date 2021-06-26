package com.example.eventsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tickResults extends AppCompatActivity {

    private List<eventD> eventDList;

    private RecyclerView recyclerView;
    private TextView noSearchResult;
    boolean noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_tick_results);
        recyclerView = findViewById(R.id.eventTable);
        noSearchResult = findViewById(R.id.noSearchResult);
        noResult = false;

        eventDList = new ArrayList<>();


        String resultTableString = getIntent().getExtras().getString("ticketevents");
        try {
            JSONObject resultTableJSON = new JSONObject(resultTableString);
            JSONArray events = resultTableJSON.getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject oneEvent = events.getJSONObject(i);
                 eventD eventd = new eventD();

                eventd.setEvent(oneEvent.getString("event"));



                eventd.setId(oneEvent.getString("id"));

                String category = oneEvent.getString("genre");
                String cats[]=category.split("\\|");
                String mainCat=cats[2].trim();

                eventd.setGenre(category);
                eventd.setVenue(oneEvent.getString("venue"));
                eventd.setDatetime(oneEvent.getString("datetime"));

//                anime.setLocalDate(singleEvent.getJSONObject("dates").getJSONObject("start").getString("localDate"));
//                anime.setLocalTime(singleEvent.getJSONObject("dates").getJSONObject("start").getString("localTime"));
//
//                anime.setVenue(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));
//
//                anime.setLat(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("latitude"));
//
//                anime.setLng(singleEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("longitude"));

                int catImg;
                if(mainCat.contains("Music") || mainCat.equals("Music") || mainCat.equals("music")){
                    catImg = R.drawable.music_icon;
                }
                else if(mainCat.contains("Sports") || mainCat.equals("Sports") || mainCat.equals("sports")){
                    catImg = R.drawable.ic_sport_icon;
                }
                else if(mainCat.contains("Arts") || mainCat.equals("Arts & Theatre") || mainCat.equals("Arts&Theatre") ||mainCat.equals("arts & theatre") || mainCat.equals("arts&theatre")){
                    catImg = R.drawable.art_icon;
                }
                else if(mainCat.contains("Miscellaneous") || mainCat.equals("Miscellaneous") || mainCat.equals("miscellaneous")){
                    catImg = R.drawable.miscellaneous_icon;
                }
                else{
                    catImg = R.drawable.film_icon;
                }
                eventd.setCatImage(catImg);

                eventd.setLatlng(oneEvent.getString("latlng"));



                eventDList.add(eventd);

                Log.i("Events", events.getJSONObject(i).toString());
            }
        } catch (JSONException e) {
            noResult = true;
            Log.i("ERROR", "No search result");
        }

        setRecyclerView(eventDList);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(List<eventD> eventDList) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, eventDList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (noResult) {
                            noSearchResult.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            noSearchResult.setVisibility(View.GONE);
                        }

                    }
                }, 1500);

    }


}
