package com.unlh.geoinfo;

public class Image {

    private long id;
    private String titre;
    private String imagepath;
    private double lon, lat;

    public Image() {
    }

    public Image(String titre, double lon, double lat, String imagepath) {
        this.titre = titre;
        this.imagepath = imagepath;
        this.lon = lon;
        this.lat = lat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
