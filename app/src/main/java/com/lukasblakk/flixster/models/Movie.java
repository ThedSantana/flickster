package com.lukasblakk.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lukas on 3/6/17.
 */

public class Movie {


    // YouTube base URL https://www.youtube.com/watch?v=
    // Movie Detail call https://api.themoviedb.org/3/movie/341174?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&append_to_response=videos,images
    // Rating = vote average and goes between 0.5 and 10
    // Google API key (for YouTube AIzaSyDXOS_LJWa-rzvsd9OQ9WehGNMhoUmTxMo)

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

    public Double getRating() { return rating; }

    public String getYouTubeKey() {

        // go to the URL for the videos
        // parse for key
        // return the first key or ""
        return "5xVh-7ywKpE";
    }


    String posterPath;
    String originalTitle;
    String overview;
    String backdropPath;
    Double rating;
    String youTubeKey;

    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.rating = jsonObject.getDouble("vote_average");
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
