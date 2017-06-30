package com.example.android.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MovieListActivity extends AppCompatActivity {
    String LOG_TAG = MovieListActivity.class.getSimpleName();

    private RecyclerView mMovieList;
    private MovieListAdapter mMoviesListAdapter;

    private static final int MOVIE_LIST_COLUMN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mMovieList = (RecyclerView) findViewById(R.id.recycler_view_movie);
        mMovieList.setLayoutManager(new GridLayoutManager(this, MOVIE_LIST_COLUMN));
        mMovieList.setHasFixedSize(true);

        mMoviesListAdapter = new MovieListAdapter(this);
        mMovieList.setAdapter(mMoviesListAdapter);
    }
}
