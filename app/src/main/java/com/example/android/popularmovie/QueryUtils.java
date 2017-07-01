package com.example.android.popularmovie;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final int CONN_READ_TIME = 10000;
    private static final int CONN_CONNECT_TIME = 15000;

    private static final String JSON_ARRAY_RESULTS = "results";
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    private static final String JSON_KEY_POSTER_PATH = "poster_path";
    private static final String JSON_KEY_OVERVIEW = "overview";
    private static final String JSON_KEY_RELEASE_DATE = "release_date";

    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(url);
        List<Movie> movies = extractFeatureFromJson(jsonResponse);

        return movies;
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        Log.v(LOG_TAG, "url : " + url);
        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(CONN_READ_TIME);
            httpURLConnection.setConnectTimeout(CONN_CONNECT_TIME);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code : " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving The Movie JSON results", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            try {
                line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem reading The JSON Response", e);
            }
        }
        return output.toString();
    }

    private static List<Movie> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) return null;

        List<Movie> movies = new ArrayList<>();
        String id = "";
        String title = "";
        String voteAverage = "";
        String posterPath = "";
        String overview = "";
        String releaseDate = "";

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            if (baseJsonResponse.has(JSON_ARRAY_RESULTS)) {
                JSONArray results = baseJsonResponse.getJSONArray(JSON_ARRAY_RESULTS);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    id = result.getString(JSON_KEY_ID);
                    title = result.getString(JSON_KEY_TITLE);
                    voteAverage = result.getString(JSON_KEY_VOTE_AVERAGE);
                    posterPath = result.getString(JSON_KEY_POSTER_PATH).substring(1); //remove '/'
                    overview = result.getString(JSON_KEY_OVERVIEW);
                    releaseDate = result.getString(JSON_KEY_RELEASE_DATE);
                    movies.add(new Movie(id, title, voteAverage, posterPath, overview, releaseDate));
                }
            } else {
                Log.i(LOG_TAG, "Not find JSON Object");
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON Exception", e);
            e.printStackTrace();
        }
        Log.i(LOG_TAG, movies.get(0).toString());
        return movies;
    }

}
