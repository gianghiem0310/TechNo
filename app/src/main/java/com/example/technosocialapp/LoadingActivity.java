package com.example.technosocialapp;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.activity.LoginActivity;
import com.example.technosocialapp.onboarding.OnBoardingActivity;

public class LoadingActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        sharedPreferences = getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT)==Enum.NULL_INT){
                    if(sharedPreferences.getInt(Enum.CHECK_USED,0)==Enum.USED){
                            go();
                      }else{
                        startActivity(new Intent(LoadingActivity.this, OnBoardingActivity.class));
                    }
                }
                else{
                    startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                }
                finish();
            }
        },1000);
    }
    private void go(){
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }
}
