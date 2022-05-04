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
import adapter.FavouriteFirstVerAdapter;
import model.FavouriteHolFirstModel;
import model.FavouriteVerFirstModel;

public class FavouriteFirstFragment extends Fragment {


    // Fav Horizontal Model list
    List<FavouriteHolFirstModel> favouriteHolFirstModelList;

    // Fav Vertical Model List
    List<FavouriteVerFirstModel> favouriteVerFirstModelList;


    // Fav Horizontal RecyclerView
    RecyclerView favHolRecyclerView;

    // Fav Vertical RecyclerView
    RecyclerView favVerRecyclerView;

    // Fav Horizontal RecyclerView
    FavouriteFirstHolAdapter favouriteFirstHolAdapter;

    // Fav Vertical RecyclerView
    FavouriteFirstVerAdapter favouriteFirstVerAdapter;
    public FavouriteFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View favFirstRoot = inflater.inflate(R.layout.fragment_favourite_hol_first, container, false);

        // *** Fav Horizontal RecyclerView
        favHolRecyclerView = favFirstRoot.findViewById(R.id.fav_hol_first_rec);
        favHolRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        favouriteHolFirstModelList = new ArrayList<>();

        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));
        favouriteHolFirstModelList.add(new FavouriteHolFirstModel(R.drawable.breakfast1, "Your Favourite food", "Nice Catch"));

        favouriteFirstHolAdapter = new FavouriteFirstHolAdapter(favouriteHolFirstModelList);
        favHolRecyclerView.setAdapter(favouriteFirstHolAdapter);


        // *** Fav Vertical RecyclerView
        favVerRecyclerView = favFirstRoot.findViewById(R.id.fav_ver_first_rec);
        favVerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        favouriteVerFirstModelList = new ArrayList<>();

        favouriteVerFirstModelList.add(new FavouriteVerFirstModel(R.drawable.verhambuger, "Vertical Food", "Good Catch"));
        favouriteVerFirstModelList.add(new FavouriteVerFirstModel(R.drawable.verhambuger, "Vertical Food", "Good Catch"));
        favouriteVerFirstModelList.add(new FavouriteVerFirstModel(R.drawable.verhambuger, "Vertical Food", "Good Catch"));
        favouriteVerFirstModelList.add(new FavouriteVerFirstModel(R.drawable.verhambuger, "Vertical Food", "Good Catch"));
        favouriteVerFirstModelList.add(new FavouriteVerFirstModel(R.drawable.verhambuger, "Vertical Food", "Good Catch"));

        favouriteFirstVerAdapter = new FavouriteFirstVerAdapter(favouriteVerFirstModelList);
        favVerRecyclerView.setAdapter(favouriteFirstVerAdapter);


        return favFirstRoot;
    }
}