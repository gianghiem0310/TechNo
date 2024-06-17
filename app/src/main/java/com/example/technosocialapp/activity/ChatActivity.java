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
import android.view.View;
import android.widget.TextView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.fragment.chat.AbstractFragment;
import com.example.technosocialapp.fragment.chat.ChatFragment;
import com.example.technosocialapp.fragment.chat.ChatFriendFragment;
import com.example.technosocialapp.fragment.chat.ChatGroupFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ChatActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    private AbstractFragment fragment;
    private FragmentTransaction transaction;
    public static final int CHAT = 15;
    public static final int CHAT_FRIEND = 16;
    public static final int CHAT_GROUP = 17;
    private int screenID = CHAT;
    private long idUser = -1;
    TextView techNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences = getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
        anhXa();
        setFragment();

        techNo = findViewById(R.id.title);
        techNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Enum.turnOnAndOffStatus(true,idUser);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Enum.turnOnAndOffStatus(false,idUser);
    }


    private void setFragment(){
        replacePage();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_mes:
                        screenID = CHAT;
                        replacePage();
                        break;
                    case R.id.btn_fr:
                        screenID = CHAT_FRIEND;
                        replacePage();
                        break;
                    case R.id.btn_gr:
                        screenID = CHAT_GROUP;
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
            if(screenID==CHAT){
                fragment = new ChatFragment();
            }
            if(screenID==CHAT_FRIEND){
                fragment = new ChatFriendFragment();
            }
            if(screenID==CHAT_GROUP){
                fragment = new ChatGroupFragment();
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