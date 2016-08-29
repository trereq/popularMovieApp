package com.example.tarek.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class movieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        Intent intent = getIntent();
        MDMovie movie = (MDMovie)intent.getExtras().getParcelable("movie");

        ImageView imageView = (ImageView)findViewById(R.id.movieDetails_imageView);
        Picasso.with(this).load(movie.getPoster_Path()).into(imageView);
    }
}
