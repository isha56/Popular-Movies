package com.example.android.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ubuntu on 17/6/16.
 */
public class MovieItem implements Parcelable {

    String title;
    Uri posterThumbnail;
    String plot;
    String rating;
    String releaseDate;

    public MovieItem(String title,String posterThumbnail,String plot,String rating,String releaseDate){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reqformat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date=format.parse(releaseDate);
            this.releaseDate=reqformat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.title=title;
        this.rating=rating;
        this.plot=plot;
        this.posterThumbnail=Uri.parse("http://image.tmdb.org/t/p/w342/"+posterThumbnail).buildUpon()
                .build();
    }

    private MovieItem(Parcel in){
        title=in.readString();
        plot=in.readString();
        releaseDate=in.readString();
        rating=in.readString();
        posterThumbnail= Uri.parse(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString(){
        return title+"--"+plot+"--"+releaseDate+"--"+rating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(plot);
        dest.writeString(releaseDate);
        dest.writeString(rating);
        dest.writeString(posterThumbnail.toString());
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>(){
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    } ;
}

