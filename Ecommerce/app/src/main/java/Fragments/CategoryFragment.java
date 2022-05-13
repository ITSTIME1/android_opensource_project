package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecommerce.R;
import com.google.android.material.tabs.TabLayout;

import Adapters.CartViewPagerAdapter;
import Adapters.CategoryViewPagerAdapter;

public class CategoryFragment extends Fragment {
    private TabLayout categoryTabLayout;
    private ViewPager2 categoryViewPager;
    private CategoryViewPagerAdapter categoryViewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View categoryRoot = inflater.inflate(R.layout.fragment_category, container, false);

        init(categoryRoot);
        fragmentManager();
        categoryTabLayoutSelected();

        return categoryRoot;
    }

    public void init(View categoryRoot){
        categoryTabLayout = categoryRoot.findViewById(R.id.category_tabLayout);
        categoryViewPager = categoryRoot.findViewById(R.id.category_viewPager);
    }

    public void fragmentManager(){
        categoryViewPagerAdapter = new CategoryViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        categoryViewPager.setAdapter(categoryViewPagerAdapter);
    }

    // ** Tab 시에 선택되어지는 position 값 **
    public void categoryTabLayoutSelected() {
        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0){
                    categoryViewPager.setCurrentItem(pos);
                } else if(pos == 1){
                    categoryViewPager.setCurrentItem(pos);
                } else {
                    categoryViewPager.setCurrentItem(pos);
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
        categoryViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                categoryTabLayout.selectTab(categoryTabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }


}
