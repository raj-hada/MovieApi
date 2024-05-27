package com.example.movieapi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {
    TextView movieTitle,movieRating,movieOverview;
    ImageView movieImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTitle =findViewById(R.id.movieTitle);
        movieRating =findViewById(R.id.movieRating);
        movieOverview = findViewById(R.id.movieOverView);
        movieImg = findViewById(R.id.movieImg);



        Bundle bundle  = getIntent().getExtras();
        if(bundle != null) {

            String mTitle = bundle.getString("title").toString();
            String mRating = bundle.getString("rating").toString();
            String mOverview = bundle.getString("overview").toString();
            String mPoster = bundle.getString("poster").toString();
            movieTitle.setText(mTitle);
            movieRating.setText(mRating);
            movieOverview.setText(mOverview);
            Glide.with(this).load(mPoster).into(movieImg);
        }
    }
}