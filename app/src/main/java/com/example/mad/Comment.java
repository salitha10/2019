package com.example.mad;

public class Comment {

    private String movieName;
    private String comments;
    private int ratings;
    private int id;


    public Comment(String movieName, String comments, int ratings, int id) {
        this.movieName = movieName;
        this.comments = comments;
        this.ratings = ratings;
        this.id = id;
    }


    public Comment(String movieName, String comments, int ratings) {
        this.movieName = movieName;
        this.comments = comments;
        this.ratings = ratings;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
