package com.example.eventsearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchTab extends Fragment {

    EditText keyword ,distance ,othLocationIn;
    TextView invalidKeyword,invalidLocation,latcheck;
    Spinner category,units;
    RadioGroup locationRadioGroup;
    RadioButton curloc,othloc;
    Button search, clear;
    String [] segmentIds={};
    String [] distanceunits={};
    LocationManager locationManager;
    LocationListener locationListener;
    FusedLocationProviderClient fusedLocationProviderClient;
    String selectedLoc;
    private double userLatitude;
    private double userLongitude;
    private double latitude;
    private double longitude;
    private RequestQueue ticResultsQ;
    boolean keyfail;
    boolean othfail;


    public SearchTab() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        keyword=getView().findViewById(R.id.keyword);
        keyfail=false;othfail=false;
        distance=getView().findViewById(R.id.distance);
        othLocationIn=getView().findViewById(R.id.location);
        othLocationIn.setEnabled(false);

        category=getView().findViewById(R.id.category);
        units=getView().findViewById(R.id.units);
        locationRadioGroup=getView().findViewById(R.id.locationRadioGroup);
        search=getView().findViewById(R.id.search);
        clear=getView().findViewById(R.id.clear);
        curloc=getView().findViewById(R.id.curloc);
        othloc=getView().findViewById(R.id.othloc);

        selectedLoc="curloc";


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
                //othLocationIn.setVisibility(View.GONE);
            }
        });

        othloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othLocationIn.setEnabled(true);
            }
        });

        ticResultsQ = Volley.newRequestQueue(this.getContext());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyword.getText().toString().length() > 0) {
//                    Log.i("Keyword: ", keywordInput.getText().toString());
                } else {
//                    Log.i("Keyword: ", "No input");

                    keyfail = true;


                }



                if (curloc.isChecked()) {
                    selectedLoc="curloc";
//                    Log.i("From: ", "Current Location");
                } else if (othloc.isChecked()) {
                    selectedLoc="othloc";
                    if (othLocationIn.getText().toString().length() > 0) {
//                        Log.i("From: ", otherLocationInput.getText().toString());
                    } else {
//                        Log.i("From: ", "No input");
                        othfail=true;
                    }
                }

                if (keyword.getText().toString().length() > 0) {
                    if (curloc.isChecked() || othloc.isChecked() && othLocationIn.getText().toString().length() > 0) {

                        sendData();
                    }
                }
                if(keyfail==true){
                    keyword.setError("Please enter the mandatory field");
                }
                if(othfail==true){
                    othLocationIn.setError("Please enter the mandatory field");
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

    private void sendData() {
        String latlng = Double.toString(userLatitude) + "," + Double.toString(userLongitude);
        String location_val;
        if(selectedLoc=="curloc") {
            location_val = latlng;
        }
        else{
            location_val=othLocationIn.getText().toString();
        }
        String category_val;
        if (category.getSelectedItem().toString().equals("Music")) {
            category_val = "KZFzniwnSyZfZ7v7nJ";
        } else if (category.getSelectedItem().toString().equals("Sports")) {
            category_val = "KZFzniwnSyZfZ7v7nE";
        } else if (category.getSelectedItem().toString().equals("Arts & Theatre")) {
            category_val = "KZFzniwnSyZfZ7v7na";
        } else if (category.getSelectedItem().toString().equals("Film")) {
            category_val = "KZFzniwnSyZfZ7v7nn";
        } else if (category.getSelectedItem().toString().equals("Miscellaneous")) {
            category_val = "KZFzniwnSyZfZ7v7nE1";
        } else {
            category_val = "All";
        }

        String distance_val;
        if (distance.getText().toString().length() > 0) {
            distance_val = distance.getText().toString();
        } else {
            distance_val = "10";
        }

        String units_val;
        if (units.getSelectedItem().toString().equals("miles")) {
            units_val = "miles";
        } else {
            units_val = "km";
        }


        String url= "https://csci571-rsbv-hw8.wl.r.appspot.com/getticket?"
            + "keyword=" + keyword.getText().toString().replace(" ","+")
            + "&category=" + category_val
            + "&distance=" + distance_val
            + "&unit=" + units_val
            + "&location=" + selectedLoc
            + "&location2="+ location_val;


        Log.i("url: ", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        latcheck=getView().findViewById(R.id.latcheck);
//                        latcheck.setText(response.toString());
                        Log.i("Resp",response.toString());
                        sendResults(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        ticResultsQ.add(request);
    }

    private void sendResults(String resp) {
        Intent intent = new Intent(this.getActivity(), tickResults.class);
        intent.putExtra("ticketevents", resp);
        startActivity(intent);
    }


    private void clearAll() {
        keyfail=false;
        othfail=false;
        keyword.setError(null);
        othLocationIn.setError(null);
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



    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getActivity().getSystemService(getContext().LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location curlocation) {
                    userLatitude = curlocation.getLatitude();
                    userLongitude = curlocation.getLongitude();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };


            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

        }

        else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 4);
        }







        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tab, container, false);
    }
    
    
    

}