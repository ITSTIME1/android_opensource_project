package com.example.mavel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;
import java.util.Random;

import adapters.RetrofitMarvelAdapter;
import adapters.RetrofitMarvelAvengersAdapter;
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

    private RecyclerView marvelAvengersRec;
    private RecyclerView marvelRec;

    private RetrofitMarvelAvengersAdapter retrofitMarvelAvengersAdapter;
    private RetrofitMarvelAdapter retrofitMarvelAdapter;

    private List<Result> resultList;
    private List<Result> resultList2;

    private MarvelResponse marvelResponse;
    private RecyclerViewDecoration recyclerViewDecoration;
    private TBDMMarvelService tbdmMarvelService;
    private TextView firstTextView;


    private final int randomPageNumberList = new Random().nextInt();
    private int replacePageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /**
         * [Validate PageNumber]
         *
         * because I used to Random().nextInt() :)
         */
        validatePageNumberList(randomPageNumberList);


        initialize();


        getMovieList();
        getAvengersList();
        firstTextView();
        searchMovie();


    }

    public void validatePageNumberList(int randomPageNumberList){
        if(randomPageNumberList <= 5) {
            replacePageNumber = randomPageNumberList;
        } else {
            replacePageNumber = 1;
        }

    }

    /**
     * [Initialize Reference]
     */
    public void initialize() {
        marvelRec = findViewById(R.id.marvel_rec);
        marvelAvengersRec = findViewById(R.id.avengers_rec);

        marvelRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        marvelAvengersRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        recyclerViewDecoration = new RecyclerViewDecoration(marvelRec.getWidth() / 2);

        marvelRec.addItemDecoration(recyclerViewDecoration);
        marvelAvengersRec.addItemDecoration(recyclerViewDecoration);
    }

    /**
     * [Retrofit getMovieList Method]
     */
    public void getMovieList(){
        tbdmMarvelService = TBDMRetrofitClient.getInstance().create(TBDMMarvelService.class);
        Call<MarvelResponse> call = tbdmMarvelService.getMarvelMovieList(Constants.API_KEY, "marvel", replacePageNumber);

        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {

                if(response.body() != null) {
                    try {
                        marvelResponse = response.body();
                        resultList = marvelResponse.getResults();
                        retrofitMarvelAdapter = new RetrofitMarvelAdapter(resultList, getApplicationContext());
                        marvelRec.setAdapter(retrofitMarvelAdapter);
                        marvelRec.setHasFixedSize(true);
                        marvelRec.setNestedScrollingEnabled(true);
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(marvelRec);

                CircleIndicator2 indicator = findViewById(R.id.indicator);
                indicator.attachToRecyclerView(marvelRec, pagerSnapHelper);
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                System.out.println(t);
            }
        });

    }

    /**
     * [Retrofit getAvengersList]
     */
    public void getAvengersList() {
        tbdmMarvelService = TBDMRetrofitClient.getInstance().create(TBDMMarvelService.class);
        Call<MarvelResponse> avengersCall = tbdmMarvelService.getMarvelMovieList(Constants.API_KEY, "avengers", replacePageNumber);

        avengersCall.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {
                if(response.body() != null) {
                    try {
                        marvelResponse = response.body();
                        resultList2 = marvelResponse.getResults();
                        retrofitMarvelAvengersAdapter = new RetrofitMarvelAvengersAdapter(resultList2, getApplicationContext());
                        marvelAvengersRec.setAdapter(retrofitMarvelAvengersAdapter);
                        marvelAvengersRec.setHasFixedSize(true);
                        marvelAvengersRec.setNestedScrollingEnabled(true);
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


    /**
     * [Search Query Method]
     * @param query
     * @param pageNumber
     */
    public void getSearchQuery(String query, int pageNumber) {
        Call<MarvelResponse> querySearch = tbdmMarvelService.getMarvelMovieList(Constants.API_KEY, query, pageNumber);
        querySearch.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {
                if(response.body() != null) {
                    try {
                        marvelResponse = response.body();
                        resultList2 = marvelResponse.getResults();
                        retrofitMarvelAvengersAdapter = new RetrofitMarvelAvengersAdapter(resultList2, getApplicationContext());
                        marvelAvengersRec.setAdapter(retrofitMarvelAvengersAdapter);
                        marvelAvengersRec.setHasFixedSize(true);
                        marvelAvengersRec.setNestedScrollingEnabled(true);
                    }catch (Exception e) {
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

    /**
     * [Go to first moveList]
     */
    public void firstTextView(){
        firstTextView = findViewById(R.id.first_text);
        firstTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marvelRec.scrollToPosition(0);
            }
        });
    }


    /**
     * [Query Search]
     */
    public void searchMovie(){
        final SearchView searchView = findViewById(R.id.item_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null) {
                    getSearchQuery(query, replacePageNumber);
                } else {
                    getSearchQuery("avengers", replacePageNumber);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    /**
     * [RecyclerView change width class]
     */
    public static class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
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