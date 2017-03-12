package com.lukasblakk.flixster.adapters;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by lukas on 3/6/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.tvTitle) TextView title;
        @BindView(R.id.tvOverview) TextView overview;
        @BindView(R.id.ivMovieImage) ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate the data
        // Orientation values to check: Portrait = 1, Landscape = 2
        Integer orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == 1) {
            Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.poster_placeholder_port).into(viewHolder.imageView);
        } else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.poster_placeholder_land).into(viewHolder.imageView);
        }
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        // return the view
        return convertView;
    }
}
