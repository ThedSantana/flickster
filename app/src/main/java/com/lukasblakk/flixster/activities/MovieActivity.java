package com.lukasblakk.flixster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lukasblakk.flixster.R;
import com.lukasblakk.flixster.adapters.MovieArrayAdapter;
import com.lukasblakk.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.lvMovies) ListView lvItems;
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Grab the movie that is tapped on in the view
                Movie movie = movieAdapter.getItem(position);
                launchYouTubeView(movie.getYouTubeKey(), movie.isPopular());
            }
        });


        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));

                    // Fill the movie youTubeKey params
                    for (int x = 0; x < movies.size(); x++) {
                        if (movies.get(x).getYouTubeKey() == "") {
                            String videosUrl = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US", movies.get(x).getId());
                            final int position = x;
                            AsyncHttpClient videoClient = new AsyncHttpClient();

                            videoClient.get(videosUrl, new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    JSONArray videoJsonResults = null;

                                    try {
                                        videoJsonResults = response.getJSONArray("results");
                                        movies.get(position).setYouTubeKey(videoJsonResults.getJSONObject(0).getString("key"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                }
                            });
                        }
                        movieAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    public void launchYouTubeView(String youTubeKey, Boolean autoplay) {
        if (!youTubeKey.isEmpty()) {
            // first parameter is the context, second is the class of the activity to launch
            Intent i = new Intent(MovieActivity.this, YouTubePlayerActivity.class);
            // put youTube key into the bundle for access in the youTube activity
            i.putExtra("youTubeKey", youTubeKey);
            i.putExtra("autoplay", autoplay);
            startActivity(i); // brings up the second activity
        }
    }
}
