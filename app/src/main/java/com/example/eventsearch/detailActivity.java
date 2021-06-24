package com.example.eventsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class detailActivity extends AppCompatActivity {


    private TabLayout DetailsTabLayout;
    private ViewPager2 viewPager2d;
    FragmentStateAdapter pagerAdapterd;
    private int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("details", getIntent().getExtras().getString("eventID"));

//        viewPager2d = findViewById(R.id.viewPager_det);
//        viewPager2d.setUserInputEnabled(false);
//        pagerAdapterd = new detailActivity.SearchFragmentPagerAdapter(this);
//
//        viewPager2d.setAdapter(pagerAdapterd);
//
//        DetailsTabLayout=findViewById(R.id.SearchFavTab);
//
//        DetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getText().equals("DETAILS")){
//                    viewPager2d.setCurrentItem(0, true);
//                }
//                else if(tab.getText().equals("ARTIST")){
//                    viewPager2d.setCurrentItem(1, true);
//                }
//                else{
//                    viewPager2d.setCurrentItem(2,true);
//
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

}

//    private class SearchFragmentPagerAdapter extends FragmentStateAdapter {
//        public SearchFragmentPagerAdapter(detailActivity detailActivity) {
//            super(detailActivity);
//        }
//        @NonNull
//        @Override
//        public Fragment createFragment(int position) {
//
//        switch (position){
//            case 0: return new DetailTab();
//            case 1: return new ArtistTab();
//            case 2: return new VenueTab();
//            default: return new DetailTab();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return NUM_PAGES;
//    }
//    }
//}
//class SearchFragmentPagerAdapter extends FragmentStateAdapter{
//
//    public SearchFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//
//        switch (position){
//            case 0: return new SearchTab();
//            case 1: return new FavoriteTab();
//            default: return new SearchTab();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return NUM_PAGES;
//    }
//}
//}