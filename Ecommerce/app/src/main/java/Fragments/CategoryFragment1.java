package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecommerce.R;
import com.google.android.material.tabs.TabLayout;

import Adapters.CategoryViewPagerAdapter;

public class CategoryFragment1 extends Fragment {

    private TabLayout categoryTabLayout;
    private ViewPager2 categoryViewPager2;
    private CategoryViewPagerAdapter categoryViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cartFragment1Root = inflater.inflate(R.layout.fragment_category_1, container, false);


        return cartFragment1Root;
    }
}
