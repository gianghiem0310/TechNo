package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.fragment.AbstractFragment;
import com.example.technosocialapp.fragment.FriendFragment;
import com.example.technosocialapp.fragment.NewsFragment;
import com.example.technosocialapp.fragment.NotificationFragment;
import com.example.technosocialapp.fragment.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    private AbstractFragment fragment;
    private FragmentTransaction transaction;
    public static final int NEWS = 10;
    public static final int FRIEND = 11;
    public static final int ADD_POST = 12;
    public static final int NOTIFICATION = 13;
    public static final int MENU = 14;
    private int screenID = NEWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Enum.CHECK_USED,Enum.USED).commit();
        anhXa();
        setFragment();
    }
    private void setFragment(){
        replacePage();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_post:
                        screenID = NEWS;
                        replacePage();
                        break;
                    case R.id.btn_friend:
                        screenID = FRIEND;
                        replacePage();
                        break;
                    case R.id.btn_noti:
                        screenID = NOTIFICATION;
                        replacePage();
                        break;
                    case R.id.btn_menu:
                        screenID = MENU;
                        replacePage();
                        break;
                }
                return true;
            }
        });


    }
    public void replacePage(){
        if(getSupportFragmentManager().findFragmentByTag(screenID+"")!=null){
            fragment = (AbstractFragment) getSupportFragmentManager().findFragmentByTag(screenID+"");
        }else{
            if(screenID==NEWS){
                fragment = new NewsFragment();
            }
            if(screenID==FRIEND){
                fragment = new FriendFragment();
            }
            if(screenID==NOTIFICATION){
                fragment = new NotificationFragment();
            }
            if(screenID==MENU){
                fragment = new MenuFragment();
            }
        }
        if(fragment!=null){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_setting,fragment,screenID+"");
            if(getSupportFragmentManager().findFragmentByTag(screenID+"")==null){
                transaction.addToBackStack(screenID+"");
            }
            transaction.commit();
        }
    }

    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void anhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

}