package com.example.eventsearch;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES=2;
    private TabLayout StabLayout;
    private ViewPager2 viewPager2;

    FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

;       viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setUserInputEnabled(false);
        pagerAdapter = new SearchFragmentPagerAdapter(this);

        viewPager2.setAdapter(pagerAdapter);

        StabLayout=findViewById(R.id.SearchFavTab);

        StabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("SEARCH")){
                    viewPager2.setCurrentItem(0, true);
                }
                else if(tab.getText().equals("FAVOURITES")){
                    viewPager2.setCurrentItem(1, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        viewpager2 = findViewById(R.id.viewpager);
//        viewpager2.setUserInputEnabled(false);
//
//        pagerAdapter = new SearchFragmentPagerAdapter(this);
//        viewpager2.setAdapter(pagerAdapter);
//
//        sfTabLayout = findViewById(R.id.SearchFormtabLayout);
//
//        sfTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getText().equals("Search")){
//                    viewpager2.setCurrentItem(0, true);
//                }
//                else if(tab.getText().equals("Favourites")){
//                    viewpager2.setCurrentItem(1, true);
//                }
//
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
//    }
//
class SearchFragmentPagerAdapter extends FragmentStateAdapter{

        public SearchFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position){
                case 0: return new SearchTab();
                case 1: return new FavoriteTab();
                default: return new SearchTab();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}