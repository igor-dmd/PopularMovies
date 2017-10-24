package com.dmed.igor.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmed.igor.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private MoviesAdapter mMoviesAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static final String POPULARITY_SORT_OPTION = "popular";

    private static final String TOP_RATED_SORT_OPTION = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter();

        mRecyclerView.setAdapter(mMoviesAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMoviesData(POPULARITY_SORT_OPTION);
    }

    private void loadMoviesData(String sortOption) {
        showMoviesDataView();

        new FetchMoviesTask().execute(sortOption);
    }

    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {

            String sortOption = strings[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(sortOption);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                String[] parsedMovieData = null;

                JSONObject moviesJson = new JSONObject(jsonWeatherResponse);
                JSONArray moviesList = moviesJson.getJSONArray("results");

                parsedMovieData = new String[moviesList.length()];

                for (int i = 0; i < moviesList.length(); i++) {
                    JSONObject movieObject = moviesList.getJSONObject(i);

                    parsedMovieData[i] = movieObject.getString("poster_path");
                }

                return parsedMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] moviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                showMoviesDataView();

                mMoviesAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }

    }

}
