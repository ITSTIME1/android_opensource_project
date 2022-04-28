package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;

import java.util.List;

import adapters.HomeHorizontalItemAdapter;
import model.HomeHorizontalItemModel;

public class HomeFragment extends Fragment {

    RecyclerView homeHorizontalRec;
    List<HomeHorizontalItemModel> homeHorizontalItemModelList;
    HomeHorizontalItemAdapter homeHorizontalItemAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeRoot = inflater.inflate(R.layout.fragment_home, container, false);


        homeHorizontalRec = homeRoot.findViewById(R.id.hor)


        return homeRoot;
    }
}
