package com.lukasblakk.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lukasblakk.flixster.R;
import com.lukasblakk.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lukas on 3/6/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView imageView;
    }


    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_expandable_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data for the position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        // check the existing view being reused
        if (convertView == null){
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            // clear out the image
            viewHolder.imageView.setImageResource(0);

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate the data
        // Orientation portrait = 1, landscape = 2
        Integer orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == 1) {
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.imageView);
        } else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.imageView);
        }
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        // return the view
        return convertView;
    }
}
