package com.example.android.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder>{

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    private List<Movie> mMovies;
    private Context mContext;

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
        holder.moviePoster.setImageResource(R.drawable.poster_sample);
        holder.textView.setText(mMovies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView textView;

        public MovieHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_list_poster_image);
            textView = (TextView) itemView.findViewById(R.id.movie_list_test_text);
        }
    }

    public void updateItems(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
