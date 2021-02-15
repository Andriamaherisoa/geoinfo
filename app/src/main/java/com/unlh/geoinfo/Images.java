package com.unlh.geoinfo;

public class Images {

    private long id;
    private String titre;
    private String imagepath;
    private long lon, lat;

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

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
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
