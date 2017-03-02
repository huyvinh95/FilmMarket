package com.vnh.filmmarket.ApiHelper;

import com.vnh.filmmarket.model.DiscoverResponse;
import com.vnh.filmmarket.model.MovieResponse;
import com.vnh.filmmarket.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HUYVINH on 14-Feb-17.
 */

public interface ApiInterface {
    @GET("discover/movie")
    Call<DiscoverResponse> getDiscoverMovies(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<DiscoverResponse> getDiscoverLoadMore(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getPopularMoviesLoadMore(@Query("api_key") String apiKey,@Query("page") int page);

    @GET("tv/popular")
    Call<TvShowResponse> getTvShow(@Query("api_key") String apikey);

    @GET("tv/popular")
    Call<TvShowResponse> getTvShowLoadMore(@Query("api_key") String apikey,@Query("page") int page);


}
