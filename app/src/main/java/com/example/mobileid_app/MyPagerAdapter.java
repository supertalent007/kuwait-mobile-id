package com.example.mobileid_app;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.content.Context;
import androidx.lifecycle.Lifecycle;


public class MyPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 5; // Number of pages
    private final Context context;

    public MyPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a new instance of the fragment for the given position
        switch (position) {
            case 0:
                return new Data_Fragment();
            case 1:
                return new DatesFragment();
            case 2:
                return new NotificationsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
