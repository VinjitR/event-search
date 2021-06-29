package com.example.eventsearch;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistTab extends Fragment {

    RequestQueue eventdet3Q;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventdet3Q= Volley.newRequestQueue(this.getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String artists;
        if(getActivity().getIntent().hasExtra("eventID")){
            String eventId=getActivity().getIntent().getStringExtra("eventID");
            //
            // Log.i("eventfrag",eventId);
            getEventArtist(eventId);
        }
    }

    private void getEventArtist(String eventId) {
        String deturl= "https://csci571-rsbv-hw8.wl.r.appspot.com/getdetails?"+"id="+eventId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, deturl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        latcheck=getView().findViewById(R.id.latcheck);
//                        latcheck.setText(response.toString());
                        getArtistsName(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        eventdet3Q.add(request);
    }

    private void getArtistsName(JSONObject response) {
        String artistOrTeam2="";
        StringBuilder artistTeams2= new StringBuilder();
        try{

            JSONArray attractions= response.getJSONObject("_embedded").getJSONArray("attractions");
            for (int i = 0; i < attractions.length(); i++) {
                String aname = attractions.getJSONObject(i).getString("name");
                if (i == 0) {
                    artistTeams2.append(aname);
                } else {
                    artistTeams2.append(",");
                    artistTeams2.append(aname);
                }
            }
            artistOrTeam2=artistTeams2.toString();
            Log.i("ssdp",artistOrTeam2);


    }
        catch (JSONException e){
            artistOrTeam2="";
        }
        getSpotDetails(artistOrTeam2);
    }

    private void getSpotDetails(String artistOrTeam2) {

        if (artistOrTeam2 == "") {
            TableLayout tableLayoutnr = getView().findViewById(R.id.aTablelayot);
            TextView textViewnr = new TextView(getContext());
            TableRow tableRownr = new TableRow((getContext()));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
            textViewnr.setLayoutParams(params);
            textViewnr.setText("No Records");
            tableRownr.addView(textViewnr);

            tableLayoutnr.addView(tableRownr);
            textViewnr.setTextSize(18);
            textViewnr.setPadding(20,20,20,20);

        } else {

            String[] names = artistOrTeam2.split(",");

            for (int i = 0; i < names.length; i++) {
                Log.i("ssdp2", names[i]);
                String sptUrl = "https://csci571-rsbv-hw8.wl.r.appspot.com/spotify?Keyword=" + names[i].replace(" ", "+");
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, sptUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.i("sptdet",response.toString());
                                formatArtists(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                eventdet3Q.add(request);


            }

        }
    }

    private void formatArtists(JSONObject response) {
        Log.i("sptdet",response.toString());

        String aName="";
        String followers="";
        String popularity="";
        String aUrl="";

        try{
             followers = response.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("followers").getString("total");
             popularity = response.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getString("popularity");
            aUrl = response.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("external_urls").getString("spotify");
            aName= response.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getString("name");

            TableLayout tableLayout=getView().findViewById(R.id.aTablelayot);

            TableRow tableRow1=new TableRow((getContext()));
            TextView textView1=new TextView(getContext());
            TableRow.LayoutParams params=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            textView1.setLayoutParams(params);
            textView1.setText(aName);
            tableRow1.addView(textView1);
            tableLayout.addView(tableRow1);
            tableRow1.setGravity(Gravity.CENTER);
            textView1.setTextSize(20);

            TableRow tableRow2 = new TableRow(getContext());
            TextView textView2 = new TextView(getContext());
            TextView textView3 = new TextView(getContext());
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
            textView2.setLayoutParams(params2);
            textView3.setLayoutParams(params2);
            textView2.setText("Name");
            textView2.setTypeface(null, Typeface.BOLD);
            textView3.setText(aName);
            tableRow2.addView(textView2);
            tableRow2.addView(textView3);
            tableLayout.addView(tableRow2);

            TableRow tableRow3 = new TableRow(getContext());
            TextView textView4 = new TextView(getContext());
            TextView textView5 = new TextView(getContext());
            //TableRow.LayoutParams params3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
            textView4.setLayoutParams(params2);
            textView5.setLayoutParams(params2);
            textView4.setText("Followers");
            textView4.setTypeface(null, Typeface.BOLD);
            textView5.setText(followers);
            tableRow3.addView(textView4);
            tableRow3.addView(textView5);
            tableLayout.addView(tableRow3);


            TableRow tableRow4 = new TableRow(getContext());
            TextView textView6 = new TextView(getContext());
            TextView textView7 = new TextView(getContext());
            //TableRow.LayoutParams params3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
            textView6.setLayoutParams(params2);
            textView7.setLayoutParams(params2);
            textView6.setText("Popularity");
            textView6.setTypeface(null, Typeface.BOLD);
            textView6.setTextSize(20);
            textView6.setPadding(20,20,20,20);
            textView7.setText(popularity);
            tableRow4.addView(textView6);
            tableRow4.addView(textView7);
            tableLayout.addView(tableRow4);


            TableRow tableRow5 = new TableRow(getContext());
            TextView textView8 = new TextView(getContext());
            TextView textView9 = new TextView(getContext());
            //TableRow.LayoutParams params3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
            textView8.setLayoutParams(params2);
            textView9.setLayoutParams(params2);
            textView8.setText("Check At");
            textView8.setTypeface(null, Typeface.BOLD);





            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView9.setText(Html.fromHtml("<a href='" + aUrl + "' target='_blank'>Spotify</a>", Html.FROM_HTML_MODE_COMPACT));
            } else {
                textView9.setText(Html.fromHtml("<a href='" + aUrl + "' target='_blank'>Spotify</a>"));
            }
            textView9.setMovementMethod(LinkMovementMethod.getInstance());
            tableRow5.addView(textView8);
            tableRow5.addView(textView9);
            tableLayout.addView(tableRow5);

            textView2.setTextSize(18);
            textView2.setPadding(20,20,20,20);
            textView3.setTextSize(18);
            textView3.setPadding(20,20,20,20);
            textView4.setTextSize(18);
            textView4.setPadding(20,20,20,20);
            textView5.setTextSize(18);
            textView5.setPadding(20,20,20,20);
            textView6.setTextSize(18);
            textView6.setPadding(20,20,20,20);
            textView7.setTextSize(18);
            textView7.setPadding(20,20,20,20);
            textView8.setTextSize(18);
            textView8.setPadding(20,20,20,20);
            textView9.setTextSize(18);
            textView9.setPadding(20,20,20,20);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }
}