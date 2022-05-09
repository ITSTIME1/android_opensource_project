package Fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.ArrayList;

import Adapters.FragmentHomeHolRecAdapter;
import Models.FragmentHomeHolRecItemModel;


// ** HomeFragment **
// This fragment have multiple recyclerview and dialogs
// So if you want to add in HomeFragment you did it.

public class HomeFragment extends Fragment {

    private RecyclerView fragmentHolRec;
    private FragmentHomeHolRecAdapter fragmentHolRecAdapter;
    private ArrayList<FragmentHomeHolRecItemModel> fragmentHomeHolRecItemModelArrayList;
    private RecyclerViewDecoration recyclerViewDecoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeRoot = inflater.inflate(R.layout.fragment_home, container, false);

        // ** Reference (etc. recyclerView...decoration.. )
        fragmentHolRec = homeRoot.findViewById(R.id.home_fragment_hol_rec);
        fragmentHolRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewDecoration = new RecyclerViewDecoration(50);
        fragmentHolRec.addItemDecoration(recyclerViewDecoration);

        // ** Home Hol Rec Item List
        fragmentHomeHolRecItemModelArrayList = new ArrayList<>();
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol1, "돔바", "CASUAL STYLE", "13,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol2, "705", "CASUAL STYLE", "15,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol3, "LA DESIGN", "CASUAL STYLE", "21,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol4, "Cooling Shirts", "CASUAL STYLE", "17,000", "장바구니 담기"));



        // ** Home Hol Rec Adapter
        fragmentHolRecAdapter = new FragmentHomeHolRecAdapter(fragmentHomeHolRecItemModelArrayList, getActivity());
        fragmentHolRec.setAdapter(fragmentHolRecAdapter);




        return homeRoot;

    }

    // ** HomeHolRec 아이템 간격 조정
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
