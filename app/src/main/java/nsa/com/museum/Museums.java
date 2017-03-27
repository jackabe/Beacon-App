package nsa.com.museum;

import android.content.Context;

import java.util.Map;

/**
 * Created by c1673107 on 25/03/2017.
 */
public class Museums {
    int museumId;
    String museumCity;
    int museumOpen;
    int museumClose;

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    public String getMuseumCity() {
        return museumCity;
    }

    public void setMuseumCity(String museumCity) {
        this.museumCity = museumCity;
    }

    public int getMuseumOpen() {
        return museumOpen;
    }

    public void setMuseumOpen(int museumOpen) {
        this.museumOpen = museumOpen;
    }

    public int getMuseumClose() {
        return museumClose;
    }

    public void setMuseumClose(int museumClose) {
        this.museumClose = museumClose;
    }

//    public Museums(String museumCity, int museumOpen, int museumClose) {
//        this.museumCity = museumCity;
//        this.museumOpen = museumOpen;
//        this.museumClose = museumClose;
//    }

}
