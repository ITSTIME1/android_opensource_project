package com.example.playground;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.playground.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private List<DataModel> dataModelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout connect
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        populateData();
    }

    public void populateData(){
        if(dataModelList == null) {
            dataModelList = new ArrayList<>();

            dataModelList.add(new DataModel("Android Oreo", "8.1"));
            dataModelList.add(new DataModel("Android Oreo", "8.1"));
            dataModelList.add(new DataModel("Android Pie", "9.0"));
            dataModelList.add(new DataModel("Android Nougat", "7.0"));
            dataModelList.add(new DataModel("Android Marshmallow", "6.0"));


            TestRecyclerAdapter testRecyclerAdapter = new TestRecyclerAdapter(dataModelList, this);
            activityMainBinding.setTestingAdapter(testRecyclerAdapter);
        }

    }
}