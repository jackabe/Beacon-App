package nsa.com.museum.MainActivity;

import android.content.Context;

import java.util.Map;

/**
 * Created by c1673107 on 25/03/2017.
 */
public class Museums {
    private int museumId;
    private String museumCity;
    private int museumOpen;
    private int museumClose;

    public Museums() {

    }

    public Museums(String museumCity, int museumOpen, int museumClose) {
        this.museumCity = museumCity;
        this.museumOpen = museumOpen;
        this.museumClose = museumClose;
    }

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

}