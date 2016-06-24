package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent=getActivity().getIntent();
        Bundle b=intent.getExtras();
        if(b!=null && intent.hasExtra("MovieObject")){
            MovieItem movie= b.getParcelable("MovieObject");
            populateView(movie,rootview);
        }
        return rootview;
    }

    private void populateView(MovieItem movie, View rootview){
        ((TextView)rootview.findViewById(R.id.movie_name_tv)).setText(movie.title);
        ((TextView)rootview.findViewById(R.id.movie_plot_tv)).setText(movie.plot);
        ((TextView)rootview.findViewById(R.id.release_date_tv)).setText(movie.releaseDate);
        ((TextView)rootview.findViewById(R.id.rating_tv)).setText(movie.rating);
        ImageView posterView=(ImageView)rootview.findViewById(R.id.poster_iv);
        Picasso.with(getContext()).load(movie.posterThumbnail).into(posterView);
    }
}
