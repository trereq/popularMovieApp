package com.example.tarek.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<Integer, Void, ArrayList<MDMovie>> {

    private static final String LOG_TAG = FetchMovieTask.class.getName();

    @Override
    protected ArrayList<MDMovie> doInBackground(Integer... params) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {

            String FORECAST_BASE_URL = null;
            if (params[0] ==  1)
                FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
            else
                FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().
                    appendQueryParameter(APPID_PARAM, BuildConfig.themoviedb_MAP_API_KEY)
                    .build();
            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            // setConnectTimeout: tis the timeout for creating a connection.
            // Let's say you have an unreliable server and you want to
            // wait only 10 seconds before you tell the user that "something is wrong".
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            //ReadTimeout: is the timeout when you have a connection, but
            // you're blocked on read() or the read from server is slow, and you want to get an exception
            // if the read blocks takes more than timeout.
            urlConnection.setReadTimeout(10000 /* milliseconds */);

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true); //I didn't understand it
            urlConnection.connect();

            // getResponseCode() returns the connection's status code. This is a useful way of getting additional information about the connection.
            // A status code of 200 indicates success
            int response = urlConnection.getResponseCode();

            //Read the input stream into string
            //An InputStream is a readable source of bytes. Once you get an InputStream, it's common to decode or convert it into a target data type.
            // For example, if you were downloading image data, you will read the inputStream to a Bitmap object
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null; // Nothing to do.

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0)
                return null; // Stream was empty.  No point in parsing

            forecastJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e("MainActivity", "Error", e);
            return null;
        } finally {

            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return getMoviesDataFromJson(forecastJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<MDMovie> result) {

        if (result != null && Movies_Fragment.imageAdapter != null) {

            Movies_Fragment.imageAdapter.clear();
            Movies_Fragment.imageAdapter.addAllMovies(result);
        }
    }

    private ArrayList<MDMovie> getMoviesDataFromJson(String MoviesJsonStr)
            throws JSONException {

        final String MD_results = "results";
        final String MD_ORIGINAL_TITLE = "original_title";
        final String MD_POSTER_PATH = "poster_path";
        final String MD_OVERVIEW = "overview";
        final String MD_USER_RATING = "vote_average";
        final String MD_RELEASE_DATE = "release_date";

        JSONObject moviesJson = new JSONObject(MoviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MD_results);
        ArrayList<MDMovie> movies = new ArrayList<MDMovie>();


        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);

            MDMovie movie = new MDMovie();
            movie.setTitle(movieJson.getString(MD_ORIGINAL_TITLE));
            movie.setPoster_Path(movieJson.getString(MD_POSTER_PATH));
            movie.setOverview(movieJson.getString(MD_OVERVIEW));
            movie.setUser_rating(movieJson.getString(MD_USER_RATING));
            movie.setRelease_Date(movieJson.getString(MD_RELEASE_DATE));

            movies.add(movie);
        }
        return movies;
    }
}
