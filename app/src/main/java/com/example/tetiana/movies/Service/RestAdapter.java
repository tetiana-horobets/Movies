package com.example.tetiana.movies.Service;

import retrofit.RequestInterceptor;

public class RestAdapter {

    public static MoviesApiService getServise(){
        retrofit.RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "my_key");

                    }
                })
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(MoviesApiService.class);
    }
}
