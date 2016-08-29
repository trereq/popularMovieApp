package com.example.tarek.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tarek on 23.8.2016.
 */
public class MDMovie implements Parcelable{


    String title;
    String poster_Path;
    String overview;
    String user_rating;
    String release_Date;

    public MDMovie( ) {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }


    public String getRelease_Date() {
        return release_Date;
    }

    public void setRelease_Date(String release_Date) {
        this.release_Date = release_Date;
    }


    public String getPoster_Path() {
        return poster_Path;
    }

    public void setPoster_Path(String poster_Path) {

        this.poster_Path = "http://image.tmdb.org/t/p/w185" + poster_Path;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(title);
        parcel.writeString(poster_Path);
        parcel.writeString(overview);
        parcel.writeString(user_rating);
        parcel.writeString(release_Date);
    }

    // We reconstruct the object reading from the Parcel data
    public MDMovie(Parcel movie)
    {
        title = movie.readString();
        poster_Path = movie.readString();;
        overview = movie.readString();;
        user_rating = movie.readString();;
        release_Date = movie.readString();;

    }
    public static final Parcelable.Creator<MDMovie> CREATOR = new Creator<MDMovie>() {
        @Override
        public MDMovie createFromParcel(Parcel parcel) {
            return new MDMovie(parcel);
        }

        @Override
        public MDMovie[] newArray(int size) {
            return new MDMovie[size];
        }
    };
}
