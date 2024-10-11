package com.example.popularmovies;

public class Movie {
    private String title;
    private  String poster;
    private String rating;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public  Movie(String title, String poster, String rating, String overview){
        this.overview=overview;
        this.poster=poster;
        this.rating=rating;
        this.title=title;
    }
}
