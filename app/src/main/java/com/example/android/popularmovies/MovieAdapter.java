package com.example.android.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ubuntu on 17/6/16.
 */
public class MovieAdapter extends ArrayAdapter<MovieItem> {

    public MovieAdapter(Activity context, List<MovieItem> movieItems){
        super(context,0,movieItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieItem movieItem=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie,parent,false);
        }

//        ImageView posterView=(ImageView)convertView.findViewById(R.id.grid_item_movie_imagview);
//        Picasso.with(getContext()).load(movieItem.posterThumbnail).into(posterView);

        TextView posterView=(TextView)convertView.findViewById(R.id.grid_item_movie_imagview);
        posterView.setText(movieItem.posterThumbnail.toString());

        return convertView;
    }
}
