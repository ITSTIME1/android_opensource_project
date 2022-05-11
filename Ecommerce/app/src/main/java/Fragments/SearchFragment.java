package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.R;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private String searchViewMessage = "Search..";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View searchRoot = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = searchRoot.findViewById(R.id.searchView);
        searchView.setQuery(searchViewMessage, false);

        return searchRoot;
    }
}
