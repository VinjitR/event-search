package com.example.eventsearch;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTab newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTab extends Fragment {

    RequestQueue eventdetQ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        eventdetQ= Volley.newRequestQueue(this.getContext());
        if(getActivity().getIntent().hasExtra("eventID")){
            String eventId=getActivity().getIntent().getStringExtra("eventID");
            //
            // Log.i("eventfrag",eventId);
            getEventDet(eventId);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_tab, container, false);
    }

    private void getEventDet(String eventId) {
        String deturl= "https://csci571-rsbv-hw8.wl.r.appspot.com/getdetails?"+"id="+eventId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, deturl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        latcheck=getView().findViewById(R.id.latcheck);
//                        latcheck.setText(response.toString());
                        formatDetails(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        eventdetQ.add(request);
    }

    private void formatDetails(JSONObject resp) {
        //Log.i("detresp",resp.toString());

        String artistOrTeam= "";
        String eVenue = "";
        String eTime = "";
        String eCategory = "";
        String ePriceRange = "";
        String eTicketStatus = "";
        String eBuyTicketAt = "";
        String eSeatMap = "";
        String eventName = "";
        try{
            eventName=resp.getString("name");}
            catch (JSONException e){

            }
            try {
                eVenue = resp.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
            }
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.venuelayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try{
            StringBuilder artistTeams= new StringBuilder();
            JSONArray attractions= resp.getJSONObject("_embedded").getJSONArray("attractions");
                for (int i = 0; i < attractions.length(); i++) {
                    String aname = attractions.getJSONObject(i).getString("name");
                    if (i == 0) {
                        artistTeams.append(aname);
                    } else {
                        artistTeams.append(" | ");
                        artistTeams.append(aname);
                    }
                }
            artistOrTeam=artistTeams.toString();
            }
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.artistlayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try{
            String lDate = resp.getJSONObject("dates").getJSONObject("start").getString("localDate");
            String lTime = resp.getJSONObject("dates").getJSONObject("start").getString("localTime");
            eTime=correctDate(lDate)+" "+lTime;}
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.datelayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try{
            String cat1 = resp.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
            String cat2 = resp.getJSONArray("classifications").getJSONObject(0).getJSONObject("genre").getString("name");
            eCategory = cat1 + " | " + cat2;}
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.categorylayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try {
                String min$ = "$" + resp.getJSONArray("priceRanges").getJSONObject(0).getString("min");
                String max$ = "$" + resp.getJSONArray("priceRanges").getJSONObject(0).getString("max");
                if (min$.length() > 1 && max$.length() > 1) {
                    ePriceRange = min$ + " ~ " + max$;
                } else if (min$.length() > 1) {
                    ePriceRange = min$;
                } else {
                    ePriceRange = max$;
                }
            }
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.pricerangelayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try{
                eTicketStatus = resp.getJSONObject("dates").getJSONObject("status").getString("code");
            }
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.ticketstatuslayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
            try{
                eBuyTicketAt=resp.getString("url");
            }
            catch (JSONException e){
                LinearLayout eventTicketStatusLayout = getView().findViewById(R.id.buyticketlayot);
                eventTicketStatusLayout.setVisibility(getView().GONE);
            }
        try {
            eSeatMap = resp.getJSONObject("seatmap").getString("staticUrl");
        } catch (JSONException e) {
            LinearLayout eventSeatMapLayout = getView().findViewById(R.id.seatmapLayot);
            eventSeatMapLayout.setVisibility(getView().GONE);
        }


            setData(artistOrTeam,eVenue,eTime,eCategory,ePriceRange,eTicketStatus,eBuyTicketAt,eSeatMap);


    }

    private String correctDate(String lDate) {
        SimpleDateFormat gotDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDate = new SimpleDateFormat("MMM dd, yyyy");
        String formatedtime = "";

        try {

            formatedtime = newDate.format(gotDate.parse(lDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedtime;
    }

    private void setData(String artistOrTeam, String eVenue, String eTime, String eCategory, String ePriceRange, String eTicketStatus, String eBuyTicketAt, String eSeatMap) {

        Log.i("sendd",artistOrTeam+eBuyTicketAt+eCategory+ePriceRange+eSeatMap+eTicketStatus+eTime+eVenue);
        TextView eventArtistOrTeamsTextView = getView().findViewById(R.id.artistname);
        TextView eventVenueTextView = getView().findViewById(R.id.venue_det);
        TextView eventTimeTextView = getView().findViewById(R.id.Date_det);
        TextView eventCategoryTextView = getView().findViewById(R.id.category_name);
        TextView eventPriceRangeTextView = getView().findViewById(R.id.pricerange);
        TextView eventTicketStatusTextView = getView().findViewById(R.id.ticketstatus);
        TextView eventSeatMapTextView = getView().findViewById(R.id.seatmap);
        TextView eventBuyTicketAtTextView = getView().findViewById(R.id.buyurl);

        //Headings

        if(artistOrTeam!=""){

        eventArtistOrTeamsTextView.setText(artistOrTeam);}
        else {
            eventArtistOrTeamsTextView.setVisibility(View.GONE);



        }


        eventVenueTextView.setText(eVenue);


        eventTimeTextView.setText(eTime);


        eventCategoryTextView.setText(eCategory);


        eventPriceRangeTextView.setText(ePriceRange);


        eventTicketStatusTextView.setText(eTicketStatus);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eventBuyTicketAtTextView.setText(Html.fromHtml("<a href='" + eBuyTicketAt + "' target='_blank'>Ticketmaster</a>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            eventBuyTicketAtTextView.setText(Html.fromHtml("<a href='" + eBuyTicketAt + "' target='_blank'>Ticketmaster</a>"));
        }
        eventBuyTicketAtTextView.setMovementMethod(LinkMovementMethod.getInstance());

        Log.i("seatmap",eSeatMap);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eventSeatMapTextView.setText(Html.fromHtml("<a href='" + eSeatMap + "' target='_blank'>View Seat Map Here</a>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            eventSeatMapTextView.setText(Html.fromHtml("<a href='" + eSeatMap + "' target='_blank'>View Seat Map Here</a>"));
        }
        eventSeatMapTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}