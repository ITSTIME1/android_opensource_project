package com.example.jsonplayground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.PostsAdapter;
import api.RetrofitClient;
import model.Posts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Posts> postsList;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        fetchPosts();
    }

    public void init() {
        recyclerView = findViewById(R.id.posts_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        postsList = new ArrayList<>();
        postsAdapter = new PostsAdapter(postsList, this);
        recyclerView.setAdapter(postsAdapter);
    }

    private void fetchPosts() {
        RetrofitClient.getRetrofitClient().getPosts().enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postsList.addAll(response.body());
                    System.out.print(postsList);
                    postsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}