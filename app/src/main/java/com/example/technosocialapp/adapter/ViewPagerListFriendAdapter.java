package com.example.technosocialapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.technosocialapp.fragment.friend.AbstractFragment;
import com.example.technosocialapp.fragment.friend.ListFriendFragment;
import com.example.technosocialapp.fragment.friend.ListInviteFragment;

public class ViewPagerListFriendAdapter extends FragmentStateAdapter {
    AbstractFragment fragment;
    public static final int FRIEND = 0;
    public static final int INVITE = 1;
    FragmentActivity fragmentActivity;

    public ViewPagerListFriendAdapter(@NonNull FragmentActivity fragmentActivity) {
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
                case FRIEND:
                    fragment = new ListFriendFragment();
                    break;
                case INVITE:
                    fragment = new ListInviteFragment();
                    break;
            }
        }
        return fragment;
    }
}
