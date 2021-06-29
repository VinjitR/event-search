package com.example.eventsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class detailActivity extends AppCompatActivity {


    private TabLayout DetailsTabLayout;
    private ViewPager2 viewPager2d;
    FragmentStateAdapter pagerAdapterd;
    private int NUM_PAGES = 3;
    String eventName="";
    String eventID="";
    String eventVenue="";
    String eventTime="";
    String eveLatlng="";
    String eveCatThumb="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("details", getIntent().getExtras().getString("eventID"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.eventToolbar);
        //getSupportActionBar().hide();
        //setSupportActionBar(toolbar);

        //Log.i("detailslatlng", getIntent().getExtras().getString("eventLatlng"));

        if (this.getIntent().hasExtra("eventName")) {
                eventName=this.getIntent().getStringExtra("eventName");
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle(eventName);
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

                eventID=this.getIntent().getStringExtra("eventID");
                eventVenue=this.getIntent().getStringExtra("eventVenue");
                eventTime=this.getIntent().getStringExtra("eventTime");
                eveLatlng=this.getIntent().getStringExtra("eveLatlng");
                eveCatThumb=this.getIntent().getStringExtra("eveCatImg");


        }
        viewPager2d = findViewById(R.id.viewpager2d);
        viewPager2d.setUserInputEnabled(false);
        pagerAdapterd = new SearchFragmentPagerAdapter2(this);

        viewPager2d.setAdapter(pagerAdapterd);

        DetailsTabLayout = findViewById(R.id.eventDetailTabs);

        DetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("DETAILS")) {
                    viewPager2d.setCurrentItem(0, true);
                } else if (tab.getText().equals("ARTIST")) {
                    viewPager2d.setCurrentItem(1, true);
                } else {
                    viewPager2d.setCurrentItem(2, true);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TextView eventDetName=this.findViewById(R.id.eventName_deta);
//        eventDetName.setText(eventName);

        Button favoriteButton = this.findViewById(R.id.favoriteb);
        SharedPreferences sharedPreferences = getSharedPreferences("favorite", Context.MODE_PRIVATE);

        String sharedPreferencesName = eventID;
        if (sharedPreferences.contains(sharedPreferencesName)) {
            favoriteButton.setBackgroundResource(R.drawable.heart_fill_red);
        } else {
            favoriteButton.setBackgroundResource(R.drawable.heart_fill_white);
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("favorite", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String sharedPreferencesName = eventID;

                if (sharedPreferences.contains(sharedPreferencesName)) {
                    Toast.makeText(viewPager2d.getContext(), eventName + " was removed from favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setBackgroundResource(R.drawable.heart_fill_white);
                    editor.remove(sharedPreferencesName);
                    editor.commit();
                } else {
                    Toast.makeText(viewPager2d.getContext(), eventName + " was added to favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setBackgroundResource(R.drawable.heart_fill_red);
                    editor.putString(sharedPreferencesName, eventID+";"+eventName+";"+eventVenue+";"+eventTime+";"+eveLatlng+";"+eveCatThumb);
                    editor.apply();
                }

            }
        });


        twitterCheck(eventName, eventVenue);
    }



    private void twitterCheck(String eventName, String eventVenue) {
        Button twit=this.findViewById(R.id.twitterb);
        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twitterURL = "https://twitter.com/intent/tweet?text=Check+out "
                        + eventName
                        + " located at " + eventVenue
                        + " %23CSCI571EventSearch";
                Log.i("tw",twitterURL.replace(" ","+"));
                Uri uriUrl = Uri.parse(twitterURL.replace(" ","+"));
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });


    }

    public class SearchFragmentPagerAdapter2 extends FragmentStateAdapter {
        public SearchFragmentPagerAdapter2(detailActivity detailActivity) {
            super(detailActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return new DetailTab();
                case 1:
                    return new ArtistTab();
                case 2:
                    return new VenueTab();
                default:
                    return new DetailTab();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
