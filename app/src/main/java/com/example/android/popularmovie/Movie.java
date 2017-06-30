package com.example.android.popularmovie;

public class Movie {
    private String id;
    private String title;
    private String voteAverage;
    private String posterPath;
    private String overview; //synopsis
    private String releaseDate;

    public Movie(String id, String title, String voteAverage,
                 String posterPath, String overview, String releaseDate) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
