package Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.R;

/**
 * [SearchFragment]
 *
 * This fragment very simple.
 * You have to implementation "SearchView" and then setting primary funtions. (It's not necessary)
 */
public class SearchFragment extends Fragment{

    private SearchView searchView;
    private ConstraintLayout constraintLayout;
    private NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View searchRoot = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = searchRoot.findViewById(R.id.searchView);
        constraintLayout = searchRoot.findViewById(R.id.search_constraint_layout);
        nestedScrollView = searchRoot.findViewById(R.id.search_nested_scrollView);


        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

        return searchRoot;
    }


    // ** Keyboard Hide **

    private void hideKeyboard()
    {
        // 만약 현재 Activity 값이 없거나 현재 Foucs 되고 있는 Activity의 값이 없다면
        // 즉 다시 말해 클릭하거나 무언가 Action을 취했을때 context 정보가 없다면 키보드를 감추는 메서드.
        if (getActivity() != null && getActivity().getCurrentFocus() != null)
        {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
