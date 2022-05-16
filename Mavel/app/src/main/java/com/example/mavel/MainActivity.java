package com.example.mavel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import adapters.RetrofitMarvelAdapter;
import constants.Constants;
import me.relex.circleindicator.CircleIndicator2;
import model.Result;
import request.TBDMMarvelService;
import request.TBDMRetrofitClient;
import response.MarvelResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * [MainActivity]
 *
 * This can be show "Marvel Movie List"
 * I used "Retrofit2", "Gson", "RecyclerView"
 * I have something to tell you.
 * If you want to using the content of this project
 * Before you start this project, you have to change "API_KEY"
 * The "API_KEY" is in the "Constants" file.
 *
 * If you want to change BASE_URL no problem.
 */

public class MainActivity extends AppCompatActivity {
    private RecyclerView marvelRec;
    private RetrofitMarvelAdapter retrofitMarvelAdapter;
    private List<Result> resultList;
    private MarvelResponse marvelResponse;

    private CircleIndicator2 circleIndicator2;
    private PagerSnapHelper pagerSnapHelper;
    private RecyclerViewDecoration recyclerViewDecoration;
    private TBDMMarvelService tbdmMarvelService;
    private TBDMRetrofitClient tbdmRetrofitClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();



    }


    /**
     * [Initialize Reference]
     */
    public void initialize() {
        marvelRec = findViewById(R.id.marvel_rec);
        marvelRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewDecoration = new RecyclerViewDecoration(120);
        marvelRec.addItemDecoration(recyclerViewDecoration);
        circleIndicator2 = findViewById(R.id.indicator);



        tbdmMarvelService = tbdmRetrofitClient.getInstance().create(TBDMMarvelService.class);
        Call<MarvelResponse> call = tbdmMarvelService.getMarvelMovieList(Constants.API_KEY, "marvel", 1);

        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {
                if(response.body() != null) {
                    try {
                        marvelResponse = response.body();
                        resultList = marvelResponse.getResults();
                        retrofitMarvelAdapter = new RetrofitMarvelAdapter(resultList, getApplicationContext());

                        marvelRec.setAdapter(retrofitMarvelAdapter);

                        pagerSnapHelper.attachToRecyclerView(marvelRec);
                        circleIndicator2.attachToRecyclerView(marvelRec, pagerSnapHelper);

                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                System.out.println(t);
            }
        });


    }


    // ** HomeHolRec 아이템 간격 조정 **
    public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
        private final int divWidth;

        public RecyclerViewDecoration(int divWidth)
        {
            this.divWidth = divWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = divWidth;
        }
    }
}