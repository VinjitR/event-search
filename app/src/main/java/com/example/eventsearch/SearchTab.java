package com.example.eventsearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchTab extends Fragment {

    EditText keyword ,distance ,othLocationIn;
    TextView invalidKeyword,invalidLocation;
    Spinner category,units;
    RadioGroup locationRadioGroup;
    RadioButton curloc,othloc;
    Button search, clear;
    String [] segmentIds={};
    String [] distanceunits={};

    //private fusedLocationProviderClient fusedLocationProviderClient;

    public SearchTab() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        invalidKeyword=getView().findViewById(R.id.invalidKeyword);
        invalidKeyword.setVisibility(View.GONE);
        invalidLocation=getView().findViewById(R.id.invalidLocation);
        invalidLocation.setVisibility(View.GONE);

        keyword=getView().findViewById(R.id.keyword);
        distance=getView().findViewById(R.id.distance);
        othLocationIn=getView().findViewById(R.id.location);
        category=getView().findViewById(R.id.category);
        units=getView().findViewById(R.id.units);
        locationRadioGroup=getView().findViewById(R.id.locationRadioGroup);
        search=getView().findViewById(R.id.search);
        clear=getView().findViewById(R.id.clear);
        curloc=getView().findViewById(R.id.curloc);
        othloc=getView().findViewById(R.id.othloc);


        //fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(getContext());
        //Categories
        ArrayAdapter<CharSequence> catAdapter= ArrayAdapter.createFromResource(getContext(),R.array.categories, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(catAdapter);
        //Unit
        ArrayAdapter<CharSequence> unitAdapter=ArrayAdapter.createFromResource(getContext(),R.array.units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(unitAdapter);

        curloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othLocationIn.setText("");
                othLocationIn.setEnabled(false);
                othLocationIn.setVisibility(View.GONE);
            }
        });

        othloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othLocationIn.setEnabled(true);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyword.getText().toString().length() > 0) {
//                    Log.i("Keyword: ", keywordInput.getText().toString());
                } else {
//                    Log.i("Keyword: ", "No input");
                    invalidKeyword.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
                }

//                Log.i("Category: ", categorySpinner.getSelectedItem().toString());

//                if (distanceInput.getText().toString().length() > 0) {
//                    Log.i("Distance: ", distanceInput.getText().toString());
//                } else {
//                    Log.i("Distance: ", "10");
//                }

//                Log.i("Distance: ", distanceSpinner.getSelectedItem().toString());

                if (curloc.isChecked()) {
//                    Log.i("From: ", "Current Location");
                } else if (othloc.isChecked()) {
                    if (othLocationIn.getText().toString().length() > 0) {
//                        Log.i("From: ", otherLocationInput.getText().toString());
                    } else {
//                        Log.i("From: ", "No input");
                        invalidLocation.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
                    }
                }

                if (keyword.getText().toString().length() > 0) {
                    if (curloc.isChecked() || othloc.isChecked() && othLocationIn.getText().toString().length() > 0) {

                        //getGeoLocation();
                    }
                }
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "CLEAR BUTTON CLICKED", Toast.LENGTH_SHORT).show();
                clearAll();

            }
        });


    }

    private void clearAll() {
        invalidKeyword.setVisibility(View.GONE);
        invalidLocation.setVisibility(View.GONE);
        keyword.setText("");
        category.setSelection(0);
        distance.setText("");
        curloc.setChecked(true);
        othLocationIn.setText("");
        othLocationIn.setEnabled(false);
    }

    /*public void onViewCreated(@NonNull @NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                userLatitude = location.getLatitude();
//                userLongitude = location.getLongitude();
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//
//        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tab, container, false);
    }
}