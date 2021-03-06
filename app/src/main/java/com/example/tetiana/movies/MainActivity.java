package com.example.tetiana.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tetiana.movies.Service.RestAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    List<Movie> movies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private int menu_selection = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_show_movie);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        TextView mEmptyStateTextView = findViewById(R.id.empty_view);
        mAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMovieList(movies);
        if (!isOnline()){
            mEmptyStateTextView.setText(R.string.no_internet);
        }
        showPopularMovie();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = mAdapter.getId();
        Context context = MainActivity.this;
        Class destinationActivity = DetailsActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra("name", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        if (menu_selection != -1) {

            MenuItem selected = (MenuItem) menu.findItem(menu_selection);
            selected.setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
            menu_selection = item.getItemId();
        }

        switch (item.getItemId()) {

            case R.id.menu_popular:
                showPopularMovie();
                return true;

            case R.id.menu_top:
                showTopMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("selection", menu_selection);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        menu_selection = savedInstanceState.getInt("selection");

    }

    private void showPopularMovie() {
        RestAdapter.getServise().getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void showTopMovie() {
        RestAdapter.getServise().getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
