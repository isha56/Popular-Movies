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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    @BindView(R.id.movie_name_tv) TextView tv1;
    @BindView(R.id.movie_plot_tv) TextView tv2;
    @BindView(R.id.release_date_tv) TextView tv3;
    @BindView(R.id.rating_tv) TextView tv4;
    @BindView(R.id.poster_iv) ImageView posterView;
    private Unbinder unbinder;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void populateView(MovieItem movie, View rootview){
        unbinder=ButterKnife.bind(this,rootview);
        tv1.setText(movie.title);
        tv2.setText(movie.plot);
        tv3.setText(movie.releaseDate);
        tv4.setText(movie.rating);
        Picasso.with(getContext()).load(movie.posterThumbnail).into(posterView);
    }
}
