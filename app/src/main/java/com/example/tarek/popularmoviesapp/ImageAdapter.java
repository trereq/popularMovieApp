package com.example.tarek.popularmoviesapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 23.8.2016.
 */
public class ImageAdapter extends BaseAdapter {
    //note; you can extend the ArrayAdapter class, its better than extending the BaseAdapter
    // the ArrayAdapter class contains the reference for the data
    //and for that you don't have to make your own movies field

    public List<MDMovie>   movies = new ArrayList<MDMovie>();
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }



    public void addAllMovies(List<MDMovie> movies)
    {
        this.movies.addAll(movies);
        notifyDataSetChanged();
        //necessary to call notifyDataSetChanged()
        //because here we have updated norma list, not an ArrayAdapter as in the SunShine project
    }
    public void addMovie(MDMovie movie)
    {
        this.movies.add(movie);
        notifyDataSetChanged();
        //necessary to call notifyDataSetChanged()
        //because here we have updated norma list, not an ArrayAdapter as in the SunShine project
    }
    public void clear() {
        this.movies.clear();
    }
    @Override
    public int getCount() {
        return movies.size();
        // here the adapter knows how many item to retrieve
        //its so important to override this mothod
    }

    @Override
    public MDMovie getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.with(mContext).load(movies.get(i).getPoster_Path()).into(imageView);
        //to set the path from the local
        //imageView.setImageResource(R.drawable.ww);

        return imageView;
    }


}
