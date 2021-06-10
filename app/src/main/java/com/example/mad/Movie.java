package com.example.mad;

public class Movie {

    private String movieName;
    private int year;
    private int id;

    public Movie(String movieName, int year, int id) {
        this.movieName = movieName;
        this.year = year;
        this.id = id;
    }

    public Movie(String movieName, int year) {
        this.movieName = movieName;
        this.year = year;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
