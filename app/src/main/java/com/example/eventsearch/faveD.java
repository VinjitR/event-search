package com.example.eventsearch;

public class faveD {
    private String favevent;
    private String favdatetime;
    private String favvenue;
    private String favlatlng;
    private String favid;
    private int favcatImage;

    public faveD(){

    }
    public faveD(String favevent, String favdatetime, String favvenue, String favlatlng, String favid, int favcatImage) {
        this.favevent = favevent;
        this.favdatetime = favdatetime;
        this.favvenue = favvenue;
        this.favlatlng = favlatlng;
        this.favid = favid;
        this.favcatImage = favcatImage;
    }



    public String getFavevent() {
        return favevent;
    }

    public void setFavevent(String favevent) {
        this.favevent = favevent;
    }

    public String getFavdatetime() {
        return favdatetime;
    }

    public void setFavdatetime(String favdatetime) {
        this.favdatetime = favdatetime;
    }

    public String getFavvenue() {
        return favvenue;
    }

    public void setFavvenue(String favvenue) {
        this.favvenue = favvenue;
    }

    public String getFavlatlng() {
        return favlatlng;
    }

    public void setFavlatlng(String favlatlng) {
        this.favlatlng = favlatlng;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public int getFavcatImage() {
        return favcatImage;
    }

    public void setFavcatImage(int favcatImage) {
        this.favcatImage = favcatImage;
    }
}
