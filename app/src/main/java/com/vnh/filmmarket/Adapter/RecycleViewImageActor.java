package com.vnh.filmmarket.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vnh.filmmarket.DetailMovies;
import com.vnh.filmmarket.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HUYVINH on 13-Feb-17.
 */

public class RecycleViewImageActor extends RecyclerView.Adapter<RecycleViewImageActor.ViewHolderImgActor> {
    Context context;
    List<com.vnh.filmmarket.model.DetailMovies> detailMoviesList;

    public RecycleViewImageActor(Context context, List<com.vnh.filmmarket.model.DetailMovies> detailMovies) {
        this.context = context;
        this.detailMoviesList = detailMovies;
    }

    public class ViewHolderImgActor extends RecyclerView.ViewHolder {
        CircleImageView img;
        public ViewHolderImgActor(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.image_actor);
        }
    }

    @Override
    public ViewHolderImgActor onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.custom_image_actor,parent,false);
        ViewHolderImgActor viewHolderImgActor = new ViewHolderImgActor(v);
        return viewHolderImgActor ;
    }

    @Override
    public void onBindViewHolder(ViewHolderImgActor holder, int position) {
        com.vnh.filmmarket.model.DetailMovies detailMovies = detailMoviesList.get(position);
        Picasso.with(context).load(detailMovies.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return detailMoviesList.size();
    }


}
