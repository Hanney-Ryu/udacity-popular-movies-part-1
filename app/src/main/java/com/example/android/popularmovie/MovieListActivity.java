package com.example.android.popularmovie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    String LOG_TAG = MovieListActivity.class.getSimpleName();

    private static final String PATH_POPULAR = "popular";
    private static final String PATH_TOP_RATED = "top_rated";

    private static final int MOVIE_LIST_COLUMN = 2;

    private static final int LOADER_ID_POPULAR = 0;
    private static final int LOADER_ID_TOP_RATED = 1;

    private int mCurrentLoaderId;

    private ProgressBar mLoadingIndicator;
    private TextView mNoNetworkTextView;
    private RecyclerView mMovieList;
    private MovieListAdapter mMoviesListAdapter;
    private Loader<List<Movie>> mMovieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.movie_list_loading_indicator);
        mNoNetworkTextView = (TextView) findViewById(R.id.movie_list_no_network_text_view);
        mMovieList = (RecyclerView) findViewById(R.id.movie_list_recycler_view);
        mMovieList.setLayoutManager(new GridLayoutManager(this, MOVIE_LIST_COLUMN));
        mMovieList.setHasFixedSize(true);
        mMoviesListAdapter = new MovieListAdapter(this, new ArrayList<Movie>());
        mMovieList.setAdapter(mMoviesListAdapter);

        if(isConnected()){
            getSupportLoaderManager().initLoader(LOADER_ID_POPULAR, null, this);
            mCurrentLoaderId = LOADER_ID_POPULAR;
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mNoNetworkTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter_popular:
                if(mCurrentLoaderId == LOADER_ID_TOP_RATED){
                    getSupportLoaderManager().restartLoader(LOADER_ID_POPULAR, null, this);
                    mCurrentLoaderId = LOADER_ID_POPULAR;
                }
                return true;
            case R.id.action_filter_top_rated:
                if(mCurrentLoaderId == LOADER_ID_POPULAR){
                    getSupportLoaderManager().restartLoader(LOADER_ID_TOP_RATED, null, this);
                    mCurrentLoaderId = LOADER_ID_TOP_RATED;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int loaderId, Bundle args) {
        String pathForFilter = "";
        switch (loaderId){
            case LOADER_ID_TOP_RATED:
                pathForFilter = PATH_TOP_RATED;
                break;
            default:
                pathForFilter = PATH_POPULAR;
        }
        String requestUrlForMovieList = QueryUtils.makeRequestUrlForMovieList(pathForFilter);
        mMovieLoader = new MovieLoader(this, requestUrlForMovieList);
        return mMovieLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mLoadingIndicator.setVisibility(View.GONE);
        mMoviesListAdapter.updateItems(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        loader = null;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
