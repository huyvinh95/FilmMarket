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
import com.vnh.filmmarket.Adapter.RecycleViewDiscoverAdapter;
import com.vnh.filmmarket.ApiHelper.ApiClient;
import com.vnh.filmmarket.ApiHelper.ApiInterface;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Discover;
import com.vnh.filmmarket.model.DiscoverResponse;
import com.vnh.filmmarket.model.TvShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HUYVINH on 06-Feb-17.
 */

public class FragmentDiscover extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    RecycleViewDiscoverAdapter adapter;
    public static final String API_KEY = "2da2872d7deb4ce58368c69c997a0c24";
    int pagecout = 2;
    ApiInterface apiInterface;
    List<Discover> movies;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_discover, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_discover);

        //check api key null
        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Insert your API_KEY", Toast.LENGTH_SHORT).show();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        movies = new ArrayList<>();
        adapter = new RecycleViewDiscoverAdapter(getActivity(), movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadMore(pagecout);
                    }
                });
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Load();
        return view;
    }


    private void Load() {
        Call<DiscoverResponse> call = apiInterface.getDiscoverMovies(API_KEY);
        call.enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                if (response.isSuccessful()) {
                    movies.addAll(response.body().getResults());
                    adapter.notifyData();
                } else {
                    Log.e("Error Response :", "Response Error: " + String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void loadMore(final int page) {
        movies.add(new Discover(1));
        adapter.notifyItemInserted(movies.size()-1);
        Call<DiscoverResponse> call = apiInterface.getDiscoverLoadMore(API_KEY,page);
        call.enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                if (response.isSuccessful()){
                    pagecout++;
                    movies.remove(movies.size()-1);
                    List<Discover> m = response.body().getResults();
                    if (m.size()>0){
                        movies.addAll(m);
                        adapter.notifyData();
                    } else
                    {
                        adapter.setNoMoreData(false);
                        Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyData();
                }
            }

            @Override
            public void onFailure(Call<DiscoverResponse> call, Throwable t) {

            }
        });
    }

}

