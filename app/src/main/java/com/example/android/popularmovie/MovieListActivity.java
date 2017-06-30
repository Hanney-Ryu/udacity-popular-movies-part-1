package com.example.android.popularmovie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    String LOG_TAG = MovieListActivity.class.getSimpleName();

    private RecyclerView mMovieList;
    private MovieListAdapter mMoviesListAdapter;
    private Loader<List<Movie>> mMovieLoader;

    private static final String THE_MOVIE_DB_REQUEST_URL = "https://api.themoviedb.org/3/movie";
    private static final String PATH_POPULAR = "popular";
    private static final String PATH_TOP_RATED = "top_rated";
    private static final String PARAM_API_KEY = "api_key";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

    private static final int MOVIE_LIST_COLUMN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mMovieList = (RecyclerView) findViewById(R.id.recycler_view_movie);
        mMovieList.setLayoutManager(new GridLayoutManager(this, MOVIE_LIST_COLUMN));
        mMovieList.setHasFixedSize(true);
        mMoviesListAdapter = new MovieListAdapter(this, new ArrayList<Movie>());
        mMovieList.setAdapter(mMoviesListAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(THE_MOVIE_DB_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(PATH_POPULAR);
        uriBuilder.appendQueryParameter(PARAM_API_KEY, API_KEY);
        mMovieLoader = new MovieLoader(this, uriBuilder.build().toString());
        return mMovieLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mMoviesListAdapter.updateItems(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mMoviesListAdapter.updateItems(null);
    }
}
