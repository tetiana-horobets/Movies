package com.example.tetiana.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tetiana.movies.Service.RestAdapter;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailsActivity extends AppCompatActivity {
    private TextView tvOriginalTitle;
    private ImageView imPosterPath;
    private ImageView imDetailsActivity;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final String movie_id = getIntent().getStringExtra("name");

        imDetailsActivity = (ImageView) findViewById(R.id.backdrop_path);
        imPosterPath = (ImageView) findViewById(R.id.poster_path);
        tvOriginalTitle = (TextView) findViewById(R.id.original_title);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        tvOverview = (TextView) findViewById(R.id.overview);
        tvVoteAverage = (TextView) findViewById(R.id.vote_average);

        RestAdapter.getServise().getDatail(Integer.parseInt(movie_id), new Callback<Details>() {

            @Override
            public void success(Details movieDatail, Response response) {
                tvOriginalTitle.setText(movieDatail.getMovieOriginalTitle());
                tvReleaseDate.setText(movieDatail.getMovieReleaseDate());
                tvOverview.setText(movieDatail.getMovieOverview());
                tvVoteAverage.setText(movieDatail.getMovieVoteAverage());
                Picasso.with(getApplicationContext())
                        .load(movieDatail.getMoviePosterPath())
                        .into(imPosterPath);

                Picasso.with(getApplicationContext())
                        .load(movieDatail.getMovieBackdropPath())
                        .into(imDetailsActivity);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
