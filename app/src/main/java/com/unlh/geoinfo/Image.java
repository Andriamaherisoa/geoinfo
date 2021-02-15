package com.unlh.geoinfo;

public class Image {

    private long id;
    private String titre;
    private String imagepath;
    private String lon, lat;

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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
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
