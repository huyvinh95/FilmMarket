package com.vnh.filmmarket.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Discover;

import java.util.List;

/**
 * Created by HUYVINH on 08-Feb-17.
 */

public class RecycleViewDiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Discover> moviesList;
    public static final int TYPE_MOVIE = 0;
    public static final int TYPE_LOAD = 1;
    boolean isLoading = false, isMoreData = true;
    OnLoadMoreListener onLoadMoreListener;

    public RecycleViewDiscoverAdapter(Context context, List<Discover> moviesList) {
        this.context = context;
        this.moviesList = moviesList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_MOVIE) {
            return new MoviesHolder(layoutInflater.inflate(R.layout.custom_recycle_discover, parent, false));
        }
        return new LoadHolder(layoutInflater.inflate(R.layout.item_progress, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreData && !isLoading && onLoadMoreListener != null) {
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_MOVIE) {
            ((MoviesHolder) holder).BindData(moviesList.get(position),context);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public int getItemViewType(int position) {
        if (moviesList.get(position).getTitle() != null) {
            return TYPE_MOVIE;
        }
        return TYPE_LOAD;
    }

    public void notifyData() {
        notifyDataSetChanged();
        isLoading = false;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setNoMoreData(boolean isMoreData) {
        this.isMoreData = isMoreData;
    }

    //holer layout;
    static class MoviesHolder extends RecyclerView.ViewHolder {
        Button btnName;
        ImageView img;

        public MoviesHolder(View itemView) {
            super(itemView);
            btnName = (Button) itemView.findViewById(R.id.btn_name);
            img = (ImageView) itemView.findViewById(R.id.img_backdrop);
        }

        void BindData(Discover m, Context context) {
            btnName.setText(m.getTitle());
            Glide.with(context).load("https://image.tmdb.org/t/p/w500"+m.getBackdropPath()).into(img);

        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
}

