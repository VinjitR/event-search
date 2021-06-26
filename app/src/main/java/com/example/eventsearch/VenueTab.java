package com.example.eventsearch;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueTab extends Fragment implements OnMapReadyCallback{
    RequestQueue venQ,eventdet2Q;
    View view;
    GoogleMap map;
    MapView mapView;
    double lat;
    double lng;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_venue_tab, container, false);







        return v;
    }

    @Override
    public void onViewCreated(@NonNull View mview, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mview, savedInstanceState);
        eventdet2Q= Volley.newRequestQueue(this.getContext());
        if(getActivity().getIntent().hasExtra("eventVenue")){
            String eventVenue=getActivity().getIntent().getStringExtra("eventVenue");

            getVenueDetails(eventVenue);
        }
        if(getActivity().getIntent().hasExtra("eveLatlng")){
            String chek=getActivity().getIntent().getStringExtra("eveLatlng");
            String parts[]=chek.split(",");
            lat=Double.parseDouble(parts[0]);
            lng=Double.parseDouble(parts[1]);
        }
        mapView = getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);
//        if(getActivity().getIntent().hasExtra("eventLatlng")){
//            String Latlng=getActivity().getIntent().getStringExtra("eventLatlng");
//            //
//             Log.i("eventfrag",Latlng);
//        }


    }





    private void getVenueDetails(String venueR) {
        String venUrl= "https://csci571-rsbv-hw8.wl.r.appspot.com/getvenue?"+"key="+venueR.replace(" ","+");
        Log.i("venurl",venUrl);
        JsonObjectRequest requestr = new JsonObjectRequest(Request.Method.GET, venUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.i("venre",response.toString());
//                        String latiude= null;
//                        try {
//                            latiude = response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("latitude");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        lat=Double.parseDouble(latiude);
//                        String longitude= null;
//                        try {
//                            longitude = response.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("longitude");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        lng=Double.parseDouble(longitude);



                        formatVenue(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        eventdet2Q.add(requestr);




    }

    private void formatVenue(JSONObject res) {
        String vName = "";
        String vAddress = "";
        String vCity = "";
        String vPhoneNumber = "";
        String vOpenHours = "";
        String vGeneralR = "";
        String vChildR = "";
        try{

                Log.i("mapsdsa",lat+","+lng);

            vName = res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
            try{
                vAddress=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("address").getString("line1");
            }
            catch (JSONException e){
                LinearLayout vAddLayot=getView().findViewById(R.id.vAddlayot);
                vAddLayot.setVisibility(view.GONE);

            }
            try{
                String cityName=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name");
                String stateName=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("name");
                vCity=cityName+" , "+stateName;
            }
            catch (JSONException e){
                LinearLayout vCtyLayot=getView().findViewById(R.id.vCitylayot);
                vCtyLayot.setVisibility(view.GONE);
            }
            try{
                vPhoneNumber=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("phoneNumberDetail");

            }
            catch (JSONException e){
                LinearLayout vphlayot=getView().findViewById(R.id.vphlayot);
                vphlayot.setVisibility(view.GONE);
            }
            try{
                vOpenHours=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("openHoursDetail");

            }
            catch (JSONException e){
                LinearLayout vOpenhlayot=getView().findViewById(R.id.vOpenhlayot);
                vOpenhlayot.setVisibility(view.GONE);
            }
            try{
                vGeneralR = res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("generalRule");

            }
            catch (JSONException e){
                LinearLayout vgrlayot=getView().findViewById(R.id.vGrlayot);
                vgrlayot.setVisibility(view.GONE);
            }
            try{
                vChildR=res.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("childRule");

            }
            catch (JSONException e){
                LinearLayout vcrlayot=getView().findViewById(R.id.vCrlayot);
                vcrlayot.setVisibility(view.GONE);
            }



            setVenueDetails(vName,
                    vAddress,
                    vCity,
                    vPhoneNumber,
                    vOpenHours,
                    vGeneralR,
                    vChildR);




        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setVenueDetails(String vName, String vAddress, String vCity, String vPhoneNumber, String vOpenHours, String vGeneralR, String vChildR) {

        TextView vNameTV = getView().findViewById(R.id.vName);
        TextView vAddTV = getView().findViewById(R.id.vAddress);
        TextView vCityTV = getView().findViewById(R.id.vCity);
        TextView vPnTV = getView().findViewById(R.id.vPn);
        TextView vOpenHTV = getView().findViewById(R.id.vOpenH);
        TextView vGrTV = getView().findViewById(R.id.vGr);
        TextView vCrTV = getView().findViewById(R.id.vCr);

        vNameTV.setText(vName);
        vAddTV.setText(vAddress);
        vCityTV.setText(vCity);
        vPnTV.setText(vPhoneNumber);
        vOpenHTV.setText(vOpenHours);
        vGrTV.setText(vGeneralR);
        vCrTV.setText(vChildR);







    }
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));

        Log.i("checcklat",lat+","+lng);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 15));

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    public void onMapReady(GoogleMap googleMap) {
//
////            MapsInitializer.initialize(getContext());
////            Log.i("mapc", lat + "," + lng);
////            map = googleMap;
////            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
////            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
////            CameraPosition position = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(15).bearing(0).build();
////            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
//
//
//    }

}