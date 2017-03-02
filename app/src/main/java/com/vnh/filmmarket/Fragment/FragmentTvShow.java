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
import com.vnh.filmmarket.Adapter.RecycleViewTvShowAdapter;
import com.vnh.filmmarket.ApiHelper.ApiClient;
import com.vnh.filmmarket.ApiHelper.ApiInterface;
import com.vnh.filmmarket.MainActivity;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.TvShow;
import com.vnh.filmmarket.model.TvShowResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HUYVINH on 06-Feb-17.
 */

public class FragmentTvShow extends android.support.v4.app.Fragment {
    RecycleViewTvShowAdapter adapter;
    RecyclerView recycleview_tvshow;
    List<TvShow> tvshowList;
    ApiInterface apiInterface;
    int pageCount = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_tvshow,container,false);
        recycleview_tvshow = (RecyclerView) view.findViewById(R.id.recycleview_tvshows);
        tvshowList = new ArrayList<>();

        adapter = new RecycleViewTvShowAdapter(getActivity(),tvshowList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recycleview_tvshow.setLayoutManager(layoutManager);
        recycleview_tvshow.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recycleview_tvshow.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadMore(2);
                    }
                });
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Load();


        return view;
    }

    private void Load() {
        Call<TvShowResponse> call = apiInterface.getTvShow(MainActivity.API_KEY);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()){
                    tvshowList.addAll(response.body().getResults());
                    adapter.notifyData();
                } else {
                    Log.e("TVshow",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.e("Error TVshow",t.toString());
            }
        });
    }

    private void LoadMore(int page){
        tvshowList.add(new TvShow(1));
        adapter.notifyItemInserted(tvshowList.size()-1);
        Call<TvShowResponse> call = apiInterface.getTvShowLoadMore(MainActivity.API_KEY,page);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()){
                    tvshowList.remove(tvshowList.size()-1);
                    pageCount++;
                    List<TvShow> tvshow = response.body().getResults();
                    if (tvshow.size()>0){
                        tvshowList.addAll(tvshow);
                        adapter.notifyData();
                    } else {
                        adapter.setNoMoreData(true);
                        Toast.makeText(getActivity(), "No More Data", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.e("TVshowLoadMore",String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.e("Error",t.toString());
            }
        });
    }


}