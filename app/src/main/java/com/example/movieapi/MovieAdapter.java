package com.example.movieapi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private ArrayList<MovieData> arr;

    public void setFilterList(ArrayList<MovieData> filterList){
        this.arr = filterList;
        notifyDataSetChanged();

    }

    public MovieAdapter(ArrayList<MovieData> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieData movie = arr.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setText(String.valueOf(movie.getRating().toString()));
        holder.movieOverView.setText(movie.getOverView());
        Glide.with(holder.itemView.getContext()).load(movie.getPoster()).into(holder.movieImg);
        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), MovieDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", movie.getTitle());
                bundle.putString("rating", movie.getRating().toString());
                bundle.putString("overview", movie.getOverView());
                bundle.putString("poster", movie.getPoster());
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImg;
        TextView movieTitle , movieRating ,movieOverView;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.movieImg);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieRating = itemView.findViewById(R.id.movieRating);
            movieOverView = itemView.findViewById(R.id.movieOverview);
            llRow = itemView.findViewById(R.id.llrow);
        }
    }



}
