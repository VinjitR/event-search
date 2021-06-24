package com.example.eventsearch;

public class eventD {
    private String event;
    private String datetime;
    private String genre;
    private String venue;

    private String id;


    private int catImage;

    public eventD(){

    }
    public eventD(String event,String id, String datetime,String genre, String venue, int catImage) {
        this.event = event;
        this.id = id;
        this.datetime = datetime;
        this.genre = genre;
        this.venue = venue;
        this.catImage=catImage;
    }




    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

}

//    public eventD(String event, String venue, String localDate, String localTime, String classifications, String eventID, String imageURL, String lat, String lng, String ticketMasterURL) {
//        this.name = name;
//        this.venue = venue;
//        this.localDate = localDate;
//        this.localTime = localTime;
//        this.classifications = classifications;
//        this.eventID = eventID;
//        this.imageURL = imageURL;
//        this.lat = lat;
//        this.lng = lng;
//        this.ticketMasterURL = ticketMasterURL;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getVenue() {
//        return venue;
//    }
//
//    public String getLocalDate() {
//        return localDate;
//    }
//
//    public String getLocalTime() {
//        return localTime;
//    }
//
//    public String getClassifications() {
//        return classifications;
//    }
//
//    public String getEventID() {
//        return eventID;
//    }
//
//    public String getImageURL() { return imageURL; }
//
//    public String getLat() { return lat; }
//
//    public String getLng() { return lng; }
//
//    public String getTicketMasterURL() { return ticketMasterURL; }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setVenue(String venue) {
//        this.venue = venue;
//    }
//
//    public void setLocalDate(String localDate) {
//        this.localDate = localDate;
//    }
//
//    public void setLocalTime(String localTime) {
//        this.localTime = localTime;
//    }
//
//    public void setClassifications(String classifications) { this.classifications = classifications; }
//
//    public void setEventID(String eventID) {
//        this.eventID = eventID;
//    }
//
//    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
//
//    public void setLat(String lat) { this.lat = lat; }
//
//    public void setLng(String lng) { this.lng = lng; }
//
//    public void setTicketMasterURL(String ticketMasterURL) { this.ticketMasterURL = ticketMasterURL; }
//}
