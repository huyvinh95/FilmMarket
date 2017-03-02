package com.vnh.filmmarket.Adapter;

import android.content.ContentValues;
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
import com.vnh.filmmarket.model.TvShow;

import java.util.List;

/**
 * Created by HUYVINH on 09-Feb-17.
 */

public class RecycleViewTvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<TvShow> tvShowList;
    OnLoadMoreListener onLoadMoreListener;
    boolean isMoreData = true, isLoading = false;
    private static final int TYPE_MOVIE = 0;
    private static final int TYPE_LOAD =1;

    public RecycleViewTvShowAdapter(Context context, List<TvShow> tvShowList) {
        this.context = context;
        this.tvShowList = tvShowList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_MOVIE){
            return new ViewHolderTvshow (layoutInflater.inflate(R.layout.custom_recycle_tvshow,parent,false));
        }
            return new LoadHolder(layoutInflater.inflate(R.layout.item_progress,parent,false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreData && !isLoading && onLoadMoreListener != null ){
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_MOVIE){
            ((ViewHolderTvshow)holder).bindData(tvShowList.get(position),context);
        }
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public int getItemViewType(int position){
        if (tvShowList.get(position).getName() !=null) {
            return TYPE_MOVIE;
        }
        return TYPE_LOAD;
    }

    public void notifyData(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setNoMoreData(boolean isMoreData){
        this.isMoreData = isMoreData;
    }

    //View holder
    static class ViewHolderTvshow extends RecyclerView.ViewHolder{
        ImageView imgTvshow;
        TextView title;
        TextView details;
        TextView rating;
        TextView years;
        public ViewHolderTvshow(View itemView) {
            super(itemView);
            imgTvshow = (ImageView) itemView.findViewById(R.id.img_tvshow);
            title = (TextView) itemView.findViewById(R.id.title_tvshow);
            details = (TextView) itemView.findViewById(R.id.detail_tvshow);
            rating = (TextView) itemView.findViewById(R.id.rating_tvshow);
            years = (TextView) itemView.findViewById(R.id.year_tvshow);
        }

        void bindData(TvShow tvShow, Context context){

            title.setText(tvShow.getName());
            details.setText(tvShow.getOverview());
            rating.setText("IMDB :" + tvShow.getVoteAverage());
            years.setText(tvShow.getFirstAirDate().substring(0,4));
            Glide.with(context).load("https://image.tmdb.org/t/p/w500"+tvShow.getPosterPath()).into(imgTvshow);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{

        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

}
