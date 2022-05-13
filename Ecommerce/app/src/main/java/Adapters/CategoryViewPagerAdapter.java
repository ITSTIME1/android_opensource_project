package Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import Fragments.CartFragment1;
import Fragments.CartFragment2;
import Fragments.CartFragment3;
import Fragments.CategoryFragment1;
import Fragments.CategoryFragment2;
import Fragments.CategoryFragment3;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    public CategoryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public CategoryViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public CategoryViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    // @TODO Category Fragment Connect into CategoryFragment.

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CategoryFragment2();
            case 2:
                return new CategoryFragment3();
            default:
                return new CategoryFragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
