package com.example.android.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder>{

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;

    public MovieListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.moviePoster.setImageResource(R.drawable.poster_sample);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;

        public MovieHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_list_poster_image);
        }
    }
}
