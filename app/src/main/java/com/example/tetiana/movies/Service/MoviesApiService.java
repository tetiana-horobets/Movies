package com.example.tetiana.movies.Service;

import com.example.tetiana.movies.Details;
import com.example.tetiana.movies.Movie;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/{movie_id}")
    void getDatail(
            @Path("movie_id") int id,
            Callback<Details> callback
    );
}
