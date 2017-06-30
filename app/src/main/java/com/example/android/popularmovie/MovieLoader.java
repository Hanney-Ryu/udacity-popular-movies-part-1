package com.example.android.popularmovie;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        return QueryUtils.fetchMovieData(mUrl);
    }
}
