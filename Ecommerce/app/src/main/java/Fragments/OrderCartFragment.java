package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecommerce.R;
import com.google.android.material.tabs.TabLayout;

import Adapters.CartViewPagerAdapter;


/**
 * [OrderCartFragment]
 *
 * This fragment has "TabLayout" and "ViewPager"
 * before when you clone this project you should be study "fragment lifecycle"
 * because I did face fragmentManager lifecycle associated with fragment lifecycle and navigationView
 * In fact, I don't know correctly lifecycle until but i know that it's important to keep trying.
 */
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


    // @TODO FragmentLifeCycle associated with ViewPager2
    public void fragmentManager(){
        cartViewPagerAdapter = new CartViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        cartViewPager.setAdapter(cartViewPagerAdapter);
    }

    // ** Tab 시에 선택되어지는 position 값 **
    public void tabLayoutSelected() {
        cartTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0){
                    cartViewPager.setCurrentItem(pos);
                } else if(pos == 1){
                    cartViewPager.setCurrentItem(pos);
                } else {
                    cartViewPager.setCurrentItem(pos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        // ** 페이지 이동시에 이벤트를 주는 리스너 **
        // 1. 위에서 tabLayout 했을때 위치값(position) 값을 cartViewPager로 넘겨주었으며
        // 2. override 메서드 중 pageSelected 되었을 때 tabLayout의 있는 position 값을 cartTabLayout.selectTab에 넘겨주어
        // 3. TabLayout 또한 바뀌게 해준다.
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
