package com.example.technosocialapp.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.MainActivity;
import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewPagerAdapter;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

public class OnBoardingActivity extends AppCompatActivity {

    TextView skip;
    Button tiepTuc;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ViewPagerAdapter viewPagerAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Enum.CHECK_USED,0).commit();
        anhXa();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        circleIndicator.setViewPager(viewPager);
        setSuKien();
    }
    private void setSuKien(){
        tiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() < 2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }else{
                    go();
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();
            }
        });
    }
    private void go(){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }
    private void anhXa(){
        skip = findViewById(R.id.skip);
        tiepTuc = findViewById(R.id.btnNext);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.indicate);
    }
}