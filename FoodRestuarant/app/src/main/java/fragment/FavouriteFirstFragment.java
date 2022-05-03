package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodrestuarant.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteFirstHolAdapter;
import model.FavouriteHolFirstModel;

public class FavouriteFirstFragment extends Fragment {

    List<FavouriteHolFirstModel> favouriteHolFirstModelList;
    RecyclerView favHolRecyclerView;
    FavouriteFirstHolAdapter favouriteFirstHolAdapter;
    public FavouriteFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View favFirstRoot = inflater.inflate(R.layout.fragment_favourite_first, container, false);

        favHolRecyclerView = favFirstRoot.findViewById(R.id.fav_hol_first_rec);
        favHolRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        favouriteHolFirstModelList = new ArrayList<>();

        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));

        favouriteFirstHolAdapter = new FavouriteFirstHolAdapter(favouriteHolFirstModelList);
        favHolRecyclerView.setAdapter(favouriteFirstHolAdapter);

        return favFirstRoot;
    }
}