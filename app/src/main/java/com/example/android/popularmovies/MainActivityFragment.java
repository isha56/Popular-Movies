package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ArrayList<MovieItem> moviesList;
    private MovieItem[] movies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            if (movies != null) {
                moviesList = new ArrayList<MovieItem>(Arrays.asList(movies));
            } else {
                updateMovies();
            }
        } else {
            moviesList = savedInstanceState.getParcelableArrayList("movies");
        }

    }

    public MainActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", moviesList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieItem>());

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem movieClicked = movieAdapter.getItem(position);
                Bundle b = new Bundle();
                b.putParcelable("MovieObject", movieClicked);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        return rootView;

    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortFilter = prefs.getString("sort_by", "popularity.desc");
        moviesTask.execute(sortFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, MovieItem[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private MovieItem[] getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            final String MDB_RESULTS = "results";
            final String MDB_RELEASEDATE = "release_date";
            final String MDB_POSTERPATH = "poster_path";
            final String MDB_PLOT = "overview";
            final String MDB_TITLE = "title";
            final String MDB_RATING = "vote_average";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);

            MovieItem[] movieItems = new MovieItem[moviesArray.length()];

            for (int i = 0; i < moviesArray.length(); i++) {
                movieItems[i] = new MovieItem(moviesArray.getJSONObject(i).getString(MDB_TITLE), moviesArray.getJSONObject(i).getString(MDB_POSTERPATH), moviesArray.getJSONObject(i).getString(MDB_PLOT), moviesArray.getJSONObject(i).getInt(MDB_RATING) + "/10", moviesArray.getJSONObject(i).getString(MDB_RELEASEDATE));
            }

            return movieItems;
        }

        @Override
        protected MovieItem[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr;

            try {

                final String MOVIESdb_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
                final String SORTBY_PARAM = "sort_by";
                final String APIKEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIESdb_BASE_URL).buildUpon()
                        .appendQueryParameter(SORTBY_PARAM, params[0])
                        .appendQueryParameter(APIKEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(MovieItem[] movieItems) {

            movies = movieItems;
            if (movieItems != null) {
                movieAdapter.clear();
                for (MovieItem movie : movieItems) {
                    movieAdapter.add(movie);
                }
            }
        }
    }
}
