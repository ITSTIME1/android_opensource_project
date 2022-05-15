package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


// ** Base url
// https://api.themoviedb.org/3/movie/
// {movie_id}, {api_key},


// ** Get HTTP Method
// /movie/{movie_id}

// Get Image
// https://image.tmdb.org/t/p/w500//6DrHO1jr3qVrViUO6s6kFiAGM7.jpg

// ** Get Image HTTP Method
///movie/{movie_id}/images

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}