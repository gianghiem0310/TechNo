package com.example.technosocialapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.technosocialapp.fragment.profile.AbstractFragment;
import com.example.technosocialapp.fragment.friend.ListFriendFragment;
import com.example.technosocialapp.fragment.friend.ListInviteFragment;
import com.example.technosocialapp.fragment.profile.ImageOfProfileFragment;
import com.example.technosocialapp.fragment.profile.PostOfProfileFragment;

public class ViewPageProfileAdapter extends FragmentStateAdapter {
    AbstractFragment fragment;
    public static final int POST = 0;
    public static final int IMAGE = 1;
    FragmentActivity fragmentActivity;

    public ViewPageProfileAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return replaceFragment(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    private Fragment replaceFragment(int screenID){
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(screenID + "") != null) {
            fragment = (AbstractFragment) fragmentManager.findFragmentByTag(screenID + "");
        } else {
            switch (screenID){
                case POST:
                    fragment = new PostOfProfileFragment();
                    break;
                case IMAGE:
                    fragment = new ImageOfProfileFragment();
                    break;
            }
        }
        return fragment;
    }
}
