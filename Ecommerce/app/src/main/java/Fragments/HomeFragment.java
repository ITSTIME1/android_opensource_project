package Fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.ArrayList;

import Adapters.FragmentHomeHolRec2Adapter;
import Adapters.FragmentHomeHolRecAdapter;
import Models.FragmentHomeHolRec2ItemModel;
import Models.FragmentHomeHolRecItemModel;


// ** HomeFragment **
// This fragment have multiple recyclerview and dialogs
// So if you want to add in HomeFragment you did it.

public class HomeFragment extends Fragment {

    private LinearLayoutCompat linearLayoutCompat;

    private RecyclerView fragmentHolRec;
    private RecyclerView fragmentHolRec2;

    private FragmentHomeHolRecAdapter fragmentHolRecAdapter;
    private FragmentHomeHolRec2Adapter fragmentHomeHolRec2Adapter;

    private ArrayList<FragmentHomeHolRecItemModel> fragmentHomeHolRecItemModelArrayList;
    private ArrayList<FragmentHomeHolRec2ItemModel> fragmentHomeHolRec2ItemModelArrayList;

    private RecyclerViewDecoration recyclerViewDecoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeRoot = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayoutCompat = homeRoot.findViewById(R.id.fragment_home_layout);
        linearLayoutCompat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

        linearLayoutCompat = homeRoot.findViewById(R.id.fragment_home_layout);
        linearLayoutCompat.setNestedScrollingEnabled(false);
        // ** Reference (etc. recyclerView...decoration.. ) **
        fragmentHolRec = homeRoot.findViewById(R.id.home_fragment_hol_rec);
        fragmentHolRec2 = homeRoot.findViewById(R.id.home_fragment_hol_rec2);

        fragmentHolRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        fragmentHolRec2.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        recyclerViewDecoration = new RecyclerViewDecoration(50);
        fragmentHolRec.addItemDecoration(recyclerViewDecoration);
        fragmentHolRec2.addItemDecoration(recyclerViewDecoration);

        // ** HomeRec ItemList & HomeRec2 ItemList **
        fragmentHomeHolRecItemModelArrayList = new ArrayList<>();
        fragmentHomeHolRec2ItemModelArrayList = new ArrayList<>();

        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol1, "돔바", "CASUAL STYLE", "13,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol2, "705", "CASUAL STYLE", "15,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol3, "LA DESIGN", "CASUAL STYLE", "21,000", "장바구니 담기"));
        fragmentHomeHolRecItemModelArrayList.add(new FragmentHomeHolRecItemModel(R.drawable.homehol4, "Cooling Shirts", "CASUAL STYLE", "17,000", "장바구니 담기"));


        fragmentHomeHolRec2ItemModelArrayList.add(new FragmentHomeHolRec2ItemModel(R.drawable.homehol7, "아이폰 12", "Mobile", "750,000", "장바구니 담기"));
        fragmentHomeHolRec2ItemModelArrayList.add(new FragmentHomeHolRec2ItemModel(R.drawable.homehol8, "아이폰 12", "Mobile", "750,000", "장바구니 담기"));
        fragmentHomeHolRec2ItemModelArrayList.add(new FragmentHomeHolRec2ItemModel(R.drawable.homehol8, "아이폰 12", "Mobile", "750,000", "장바구니 담기"));
        fragmentHomeHolRec2ItemModelArrayList.add(new FragmentHomeHolRec2ItemModel(R.drawable.homehol8, "아이폰 12", "Mobile", "750,000", "장바구니 담기"));



        // ** Home Hol Rec Adapter & Home Hol Rec2 Adapter **
        fragmentHolRecAdapter = new FragmentHomeHolRecAdapter(fragmentHomeHolRecItemModelArrayList, getActivity());
        fragmentHomeHolRec2Adapter = new FragmentHomeHolRec2Adapter(fragmentHomeHolRec2ItemModelArrayList, getActivity());

        fragmentHolRec.setAdapter(fragmentHolRecAdapter);
        fragmentHolRec2.setAdapter(fragmentHomeHolRec2Adapter);
        fragmentHolRec.setNestedScrollingEnabled(false);
        fragmentHolRec2.setNestedScrollingEnabled(false);

        return homeRoot;

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

    // ** Keyboard Hide **

    private void hideKeyboard()
    {
        if (getActivity() != null && getActivity().getCurrentFocus() != null)
        {
            // 프래그먼트기 때문에 getActivity() 사용
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
