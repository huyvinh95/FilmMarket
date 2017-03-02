package com.vnh.filmmarket.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Discover;
import com.vnh.filmmarket.model.Movie;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by HUYVINH on 08-Feb-17.
 */

public class RecycleViewMoviesAdapter extends RecyclerView.Adapter<RecycleViewMoviesAdapter.ViewHolderMovies> {
    Context context;
    List<Movie> listMovies;
    public final static int TYPE_MOVIE = 0;
    public final static int TYPE_LOAD = 1;
    boolean isLoading = false, isMoreData = true;
    OnLoadMoreListener onLoadMoreListener;

    public RecycleViewMoviesAdapter(Context context, List<Movie> listMovies) {
        this.context = context;
        this.listMovies = listMovies;
    }

    @Override
    public RecycleViewMoviesAdapter.ViewHolderMovies onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_MOVIE) {
            return new ViewHolderMovies(layoutInflater.inflate(R.layout.custom_recycle_movies, parent, false));
        }
        return new ViewHolderMovies(layoutInflater.inflate(R.layout.item_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(RecycleViewMoviesAdapter.ViewHolderMovies holder, int position) {
        if (position >= getItemCount() - 1 && isMoreData && !isLoading && onLoadMoreListener != null) {
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_MOVIE) {
            Movie d = listMovies.get(position);
            holder.title.setText(d.getTitle());
            holder.detail.setText(d.getOverview());
            holder.rating.setText("IMDB: "+String.valueOf(d.getVoteAverage()));
            holder.years.setText(String.valueOf(d.getReleaseDate()).substring(0,4));
            Glide.with(context).load("https://image.tmdb.org/t/p/w500"+d.getPosterPath()).into(holder.img);
        }

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolderMovies extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, detail, rating, years;

        public ViewHolderMovies(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_movies);
            title = (TextView) itemView.findViewById(R.id.title_movies);
            detail = (TextView) itemView.findViewById(R.id.detail_movies);
            rating = (TextView) itemView.findViewById(R.id.rating_movies);
            years = (TextView) itemView.findViewById(R.id.year_movies);

        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setNoMoreData(boolean isMoreData) {
        this.isMoreData = isMoreData;
    }

    public void notifydata() {
        notifyDataSetChanged();
        isLoading = false;
    }

//    public class LoadHolderMovies extends RecyclerView.ViewHolder {
//        public LoadHolderMovies(View itemView) {
//            super(itemView);
//        }
//    }
}
