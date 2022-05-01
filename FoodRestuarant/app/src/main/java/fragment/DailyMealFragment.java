package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.util.ArrayList;
import java.util.List;

import adapter.DailyMealVerAdapter;
import model.DailyMealModel;

public class DailyMealFragment extends Fragment {

    List<DailyMealModel> dailyMealModelList;
    RecyclerView dailyRecView;
    DailyMealVerAdapter dailyMealVerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dailyMealRoot = inflater.inflate(R.layout.fragment_daily_meal, container, false);


        dailyRecView = dailyMealRoot.findViewById(R.id.daily_meal_rec);
        dailyRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyMealModelList = new ArrayList<>();
        dailyMealModelList.add(new DailyMealModel(R.drawable.verhambuger, "Breakfast", "Description test dinner"));
        dailyMealModelList.add(new DailyMealModel(R.drawable.verhambuger, "Launch", "Description test dinner"));
        dailyMealModelList.add(new DailyMealModel(R.drawable.verhambuger,"Appetizer", "Description test dinner"));
        dailyMealModelList.add(new DailyMealModel(R.drawable.verhambuger, "Dinner", "Description test dinner"));
        dailyMealVerAdapter = new DailyMealVerAdapter(dailyMealModelList, getActivity());
        dailyRecView.setAdapter(dailyMealVerAdapter);

        dailyMealVerAdapter.notifyDataSetChanged();

        return dailyMealRoot;
    }
}
