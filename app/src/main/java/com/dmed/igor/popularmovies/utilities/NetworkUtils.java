package com.dmed.igor.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //REPLACE THE VARIABLE MOVIEDB_API_KEY WITH YOUR OWN API KEY
    private static final String MOVIEDB_API_KEY = "";

    private static final String MOVIEDB_REQUEST_BASE_URL = "http://api.themoviedb.org/3/movie/";

    public static final String MOVIEDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private static final String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String moviePath) {
        Uri builtUri = Uri.parse(MOVIEDB_REQUEST_BASE_URL).buildUpon()
                .appendPath(moviePath)
                .appendQueryParameter(API_KEY_PARAM, MOVIEDB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
