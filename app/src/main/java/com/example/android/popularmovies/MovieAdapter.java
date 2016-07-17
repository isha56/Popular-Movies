package com.example.android.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends ArrayAdapter<MovieItem> {

    @BindView(R.id.grid_item_movie_imageview) ImageView posterView;

    public MovieAdapter(Activity context, List<MovieItem> movieItems){
        super(context,0,movieItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieItem movieItem=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie,parent,false);
        }

        ButterKnife.bind(this,convertView);
        Picasso.with(getContext())
            .load(movieItem.posterThumbnail)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(posterView);

        return convertView;
    }
}
