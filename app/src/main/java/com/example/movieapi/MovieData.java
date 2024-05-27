package com.example.movieapi;

public class MovieData {
    private int id;
    private String title;
    private Double rating;
    private String poster;
    private String overView;

    public MovieData(String title, String poster, String overView , Double rating) {
        this.title = title;
        this.rating = rating;
        this.poster = poster;
        this.overView = overView;
    }

    public MovieData(int id, String title, String poster, String overView, Double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.poster = poster;
        this.overView = overView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
}
