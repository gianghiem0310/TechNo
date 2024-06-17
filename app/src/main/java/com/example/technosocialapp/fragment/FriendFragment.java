package com.example.technosocialapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.AddFriendActivity;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.adapter.ViewPagerListFriendAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FriendFragment extends AbstractFragment{
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ViewPagerListFriendAdapter adapter;
    FloatingActionButton btnAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_friend, container, false);
        anhXa(view);
        setFirst();
        setEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.title.setText("Bạn bè");
    }
    private void setEvent(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setFirst(){
        adapter = new ViewPagerListFriendAdapter(getActivity());
        viewPager2.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case ViewPagerListFriendAdapter.FRIEND:
                    tab.setText("Bạn bè");

                    break;
                case ViewPagerListFriendAdapter.INVITE:
                    tab.setText("Lời mời");
                    break;
            }
        }).attach();
        tabLayout.getTabAt(1).getOrCreateBadge().setNumber(10);
    }
    private void anhXa(View fragment){
        viewPager2 = fragment.findViewById(R.id.view_pager);
        tabLayout = fragment.findViewById(R.id.tab_layout);
        btnAdd = fragment.findViewById(R.id.btnAdd);
    }
}
