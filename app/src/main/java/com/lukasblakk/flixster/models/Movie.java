package com.lukasblakk.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lukas on 3/6/17.
 */

public class Movie {

    // Rating = vote average and goes between 0.5 and 10

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getId() { return id; }

    public String getYouTubeKey() {
        return youTubeKey;
    }

    public void setYouTubeKey(String youTubeKey) {
        this.youTubeKey = youTubeKey;
    }

    String posterPath;
    String originalTitle;
    String overview;
    String backdropPath;
    Integer id;
    String youTubeKey;

    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.id = jsonObject.getInt("id");
        this.youTubeKey = "";
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
