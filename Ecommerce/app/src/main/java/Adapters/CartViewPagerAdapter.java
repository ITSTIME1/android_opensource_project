package Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.CartFragment1;
import Fragments.CartFragment2;
import Fragments.CartFragment3;

public class CartViewPagerAdapter extends FragmentStateAdapter {

    public CartViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CartFragment2();
            case 2:
                return new CartFragment3();
            default:
                return new CartFragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
