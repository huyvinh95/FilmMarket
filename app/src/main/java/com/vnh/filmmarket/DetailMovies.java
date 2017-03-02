package com.vnh.filmmarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vnh.filmmarket.Adapter.RecycleViewImageActor;

import java.util.ArrayList;
import java.util.List;

public class DetailMovies extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewImageActor recycleViewImageActor;
    List<com.vnh.filmmarket.model.DetailMovies> detailMoviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_imgAct);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleViewImageActor = new RecycleViewImageActor(this, detailMoviesList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleViewImageActor);

        fakeData();
    }

    private void fakeData() {
        com.vnh.filmmarket.model.DetailMovies dt = new com.vnh.filmmarket.model.DetailMovies(R.drawable.char_chris);
        detailMoviesList.add(dt);

        dt = new com.vnh.filmmarket.model.DetailMovies(R.drawable.char_johasson);
        detailMoviesList.add(dt);

        dt = new com.vnh.filmmarket.model.DetailMovies(R.drawable.char_mackie);
        detailMoviesList.add(dt);

        dt = new com.vnh.filmmarket.model.DetailMovies(R.drawable.char_robert);
        detailMoviesList.add(dt);

        dt = new com.vnh.filmmarket.model.DetailMovies(R.drawable.char_stan);
        detailMoviesList.add(dt);
    }
}
