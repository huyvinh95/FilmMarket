package com.vnh.filmmarket.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vnh.filmmarket.Adapter.OnLoadMoreListener;
import com.vnh.filmmarket.Adapter.RecycleViewMoviesAdapter;
import com.vnh.filmmarket.ApiHelper.ApiClient;
import com.vnh.filmmarket.ApiHelper.ApiInterface;
import com.vnh.filmmarket.MainActivity;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Movie;
import com.vnh.filmmarket.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HUYVINH on 06-Feb-17.
 */

public class FragmentMovies extends android.support.v4.app.Fragment {

    RecycleViewMoviesAdapter adapter;

    RecyclerView recycleview_movies;

    List<Movie> movieList = new ArrayList<>();

    ApiInterface apiInterface;

    int pagecount = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_movies, container, false);
        recycleview_movies = (RecyclerView) view.findViewById(R.id.recycleview_movies);
        adapter = new RecycleViewMoviesAdapter(getActivity(), movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recycleview_movies.setLayoutManager(layoutManager);
        recycleview_movies.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recycleview_movies.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadMore(pagecount);
                    }
                });
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Load();
        return view;
    }

    private void Load() {
        Call<MovieResponse> call = apiInterface.getPopularMovies(MainActivity.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movieList.addAll(response.body().getResults());
                    adapter.notifydata();
                } else {
                    Log.e("Movies", "Response Error" + String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Error Movies", t.toString());
            }
        });
    }

    private void LoadMore(final int page) {
        movieList.add(new Movie(1));
        adapter.notifyItemInserted(movieList.size() - 1);
        Call<MovieResponse> call = apiInterface.getPopularMoviesLoadMore(MainActivity.API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    pagecount++;
                    movieList.remove(movieList.size() - 1);
                    List<Movie> m = response.body().getResults();
                    if (m.size() > 0) {
                        movieList.addAll(m);
                        adapter.notifydata();
                    } else {
                        adapter.setNoMoreData(true);
                        Toast.makeText(getActivity(), "No More Data ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Movies", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

}
