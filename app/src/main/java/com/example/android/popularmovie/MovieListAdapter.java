package com.example.android.popularmovie;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder>{

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    private List<Movie> mMovies;
    private Context mContext;

    private static final String THE_MOVIE_DB_IMAGE_REQUEST_URL = "https://image.tmdb.org/t/p/w185";


    public MovieListAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
        mContext = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_IMAGE_REQUEST_URL)
                .buildUpon()
                .appendPath(mMovies.get(position).getPosterPath());
        Picasso.with(mContext)
                .load(uriBuilder.toString())
                .into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView moviePosterImageView;

        public MovieHolder(View itemView) {
            super(itemView);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_list_poster_image);
        }
    }

    public void updateItems(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
