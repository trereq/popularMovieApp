package com.example.tarek.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public class Movies_Fragment extends Fragment {

    public static ImageAdapter imageAdapter;


    public static Movies_Fragment newInstance(int sectionNumber) {
        Movies_Fragment fragment = new Movies_Fragment();
        Bundle args = new Bundle();
        args.putInt("index", sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
        //imageAdapter.notifyDataSetChanged();

    }

    public void updateMovies() {
        FetchMovieTask movieTask = new FetchMovieTask();
        Bundle bundle = getArguments();
        int index = bundle.getInt("index");
        movieTask.execute(index);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_, container, false);



        return  rootView;
    }


}
