package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecommerce.R;
import com.google.android.material.tabs.TabLayout;

import Adapters.CartViewPagerAdapter;

public class OrderCartFragment extends Fragment {

    private TabLayout cartTabLayout;
    private ViewPager2 cartViewPager;
    private CartViewPagerAdapter cartViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderRoot = inflater.inflate(R.layout.fragment_cart, container,  false);

        // ** CartInitialize **
        cartInit(orderRoot);
        fragmentManager();
        tabLayoutSelected();

        return orderRoot;
    }

    public void cartInit(View orderRoot){
        cartTabLayout = orderRoot.findViewById(R.id.cart_tab_layout);
        cartViewPager = orderRoot.findViewById(R.id.cart_viewpager);
    }


    public void fragmentManager(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        cartViewPagerAdapter = new CartViewPagerAdapter(fm, getLifecycle());
        cartViewPager.setAdapter(cartViewPagerAdapter);
    }

    // TabBar가 선택이 되었을때 설정하는 메서드
    public void tabLayoutSelected(){
        cartTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cartViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        cartViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                cartTabLayout.selectTab(cartTabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
}
