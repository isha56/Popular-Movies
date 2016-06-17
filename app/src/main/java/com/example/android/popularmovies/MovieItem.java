package com.example.android.popularmovies;

import android.net.Uri;

/**
 * Created by ubuntu on 17/6/16.
 */
public class MovieItem {//implements Parcelable{

    String title;
    Uri posterThumbnail;
    String plot;
    String rating;
    String releaseDate;

    public MovieItem(String title,String posterThumbnail,String plot,String rating,String releaseDate){
        this.title=title;
        this.rating=rating;
        this.releaseDate=releaseDate;
        this.plot=plot;
        this.posterThumbnail=Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendPath(posterThumbnail)
                .build();
    }

//    private MovieItem(Parcel in){
//        title=in.readString();
//        plot=in.readString();
//        releaseDate=in.readString();
//        rating=in.readString();
//        posterThumbnail= Uri.parse(in.readString());
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public String toString(){
//        return title+"--"+plot+"--"+releaseDate+"--"+rating;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(title);
//        dest.writeString(plot);
//        dest.writeString(releaseDate);
//        dest.writeString(rating);
//        dest.writeString(posterThumbnail.toString());
//    }
//
//    public final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>(){
//        @Override
//        public MovieItem createFromParcel(Parcel source) {
//            return new MovieItem(source);
//        }
//
//        @Override
//        public MovieItem[] newArray(int size) {
//            return new MovieItem[size];
//        }
//    } ;
}

