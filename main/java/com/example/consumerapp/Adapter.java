package com.example.consumerapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private ArrayList<Movie> movies = new ArrayList<>();
    private final Activity activity;

    public Adapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies){
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_consumer,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, final int i) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.errorimage)
                .apply(new RequestOptions().fitCenter());

        Glide.with(viewHolder.itemView.getContext())
                .load(IMAGE_BASE_URL + getMovies().get(i).getPosterPath())
                .apply(options)
                .into(viewHolder.imgView);

        viewHolder.tvTitle.setText(getMovies().get(i).getTitle());
        viewHolder.tvReleaseDate.setText(getMovies().get(i).getDate());
        viewHolder.tvDescription.setText(getMovies().get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView tvTitle,tvReleaseDate,tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_date);
            tvDescription = itemView.findViewById(R.id.tv_desc);
        }
    }
}
