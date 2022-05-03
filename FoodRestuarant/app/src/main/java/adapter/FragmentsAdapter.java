package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fragment.FavouriteFirstFragment;
import fragment.FavouriteSecondFragment;
import fragment.FavouriteThirdFragment;

public class FragmentsAdapter extends FragmentStateAdapter {
    public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FragmentsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public FragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new FavouriteSecondFragment();
            case 2:
                return new FavouriteThirdFragment();
        }
        return new FavouriteFirstFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
