package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.fragment.AbstractFragment;
import com.example.technosocialapp.fragment.AddPostFragment;
import com.example.technosocialapp.fragment.FriendFragment;
import com.example.technosocialapp.fragment.NewsFragment;
import com.example.technosocialapp.fragment.NotificationFragment;
import com.example.technosocialapp.fragment.MenuFragment;
import com.example.technosocialapp.model.StatusOnline;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
    private DatabaseReference databaseReference;
    private long idUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Enum.CHECK_USED,Enum.USED).commit();
        idUser = sharedPreferences.getLong(Enum.ID_USER,-1);
        databaseReference = Enum.DATABASE_REFERENCE;
        anhXa();
        setFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnOnAndOffStatus(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        turnOnAndOffStatus(false);
    }

    private void turnOnAndOffStatus(boolean status){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        long currentDate = tsTemp.getTime();
        StatusOnline statusOnline = new StatusOnline(idUser,status,currentDate);
        databaseReference.child(Enum.STATUS_ONLINE_TABLE).child(idUser+"").setValue(statusOnline).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

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
                    case R.id.btn_addpost:
                        screenID = ADD_POST;
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
            if(screenID==ADD_POST){
                fragment = new AddPostFragment();
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