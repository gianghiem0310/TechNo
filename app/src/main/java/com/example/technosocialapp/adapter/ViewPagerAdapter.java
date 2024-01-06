package com.example.technosocialapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.technosocialapp.onboarding.fragment.Board1Fragment;
import com.example.technosocialapp.onboarding.fragment.Board2Fragment;
import com.example.technosocialapp.onboarding.fragment.Board3Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Board1Fragment();

            case 1:
                return new Board2Fragment();
            case 2:
                return new Board3Fragment();
            default:
                return new Board1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
