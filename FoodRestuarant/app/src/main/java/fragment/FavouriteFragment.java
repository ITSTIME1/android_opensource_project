package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodrestuarant.R;
import com.google.android.material.tabs.TabLayout;

import adapter.FragmentsAdapter;


// FavouriteFragment Fragment create
// It have three part of three fragment
public class FavouriteFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentsAdapter fragmentsAdapter;
    private FragmentActivity myContext;


    @Nullable
    @Override
    // onCreateView onCreate 이후에 뷰를 생성해준다.
    // View라는 타입의 객체를 반환해준다.
    // onCreateView()에서 inflater를 통해서 layout을 연결해주었고 정상적으로 연결된 View객체를 onCreatedView() 의 인자로 넘겨준다.
    // 전달이 잘 되었을때 fragment lifecycle이 INITIALIZED 상태로 업데이트 되었기 때문에 View의 초기값을 설정해주거나
    // Adpater 셋팅 등을 하기에 적절하다.


    // true = attachToRoot 같은 경우 지금 자식 뷰를 부모뷰에 추가하겠느냐
    // false = attachToRoot 나중에 추가하겠느냐
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favouriteFragmentView = inflater.inflate(R.layout.fragment_favourite, container, false);

        // TableLayout and ViewPager 참조
        tabLayout = favouriteFragmentView.findViewById(R.id.favourite_tab_bar);
        viewPager2 = favouriteFragmentView.findViewById(R.id.view_pager2);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragmentsAdapter = new FragmentsAdapter(fm, getLifecycle());

        viewPager2.setAdapter(fragmentsAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Featured"));
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));

        // tablayout을 선택하는 리스너 등록
        // 각각 상태에 따라 method생성.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // ViewPager의 현재 아이템의 위치정보를 index 값을 가져옵니다.
                viewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // ViewPager가 바뀔때 행동들을 핸들링한다.
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return favouriteFragmentView;
    }
}
