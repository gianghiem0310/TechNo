package com.example.technosocialapp.activity.dangky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.model.InforUser;

public class DangKyThanhCongActivity extends AppCompatActivity {


    Intent intent;
    ImageView ic_back;
    Button btnBatDau;
    SharedPreferences sharedPreferences;
    InforUser inforUser;
    ConstraintLayout bg_login;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_thanh_cong);
        intent = new Intent(this, HomeActivity.class);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa();
        getIntentBanDau();
        setSuKien();
    }
    private void setSuKien(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar(true);
                setShared(inforUser.getId(),inforUser.getName(),inforUser.getAvatar());
                progessBar(false);
                go();
            }
        });
    }
    private void setShared(long id, String name, String avatar){
        sharedPreferences.edit().putLong(Enum.ID_USER,id).commit();
        sharedPreferences.edit().putString(Enum.NAME_USER,name).commit();
        sharedPreferences.edit().putString(Enum.IMAGE_USER,avatar).commit();

    }
    private void go(){
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);

    }
    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
            bg_login.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            bg_login.setVisibility(View.INVISIBLE);
        }
    }
    private void getIntentBanDau(){
        inforUser = (InforUser) getIntent().getExtras().getSerializable("object_user");
    }
    private void anhXa(){
        ic_back = findViewById(R.id.ic_back);
        btnBatDau = findViewById(R.id.btnDangNhap);
        bg_login = findViewById(R.id.bg_login);
        progressBar = findViewById(R.id.progessbar);
    }
}