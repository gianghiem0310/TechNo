package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.model.InforUser;
import com.google.android.material.tabs.TabLayout;

import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewPageProfileAdapter;
import com.example.technosocialapp.adapter.ViewPagerListFriendAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ic_back;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ViewPageProfileAdapter viewPageProfileAdapter;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    TextView name_profile;
    CircleImageView avt_profile;
    DatabaseReference databaseReference = Enum.DATABASE_REFERENCE;
    private long idUser = -1;
    TextView diaChi;
    TextView nghe;
    TextView congTy;
    TextView soThich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa();
        progessBar(true);
        getDuLieuShared();
        setDuLieuBanDau();
        setSuKien();
        setFirst();
        progessBar(false);
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

    private void setDuLieuBanDau(){
        databaseReference.child(Enum.INFOR_USER_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InforUser inforUser = snapshot.getValue(InforUser.class);
                name_profile.setText(inforUser.getName());
                if(!inforUser.getAvatar().equals(Enum.NULL)) {
                    Glide.with(getApplicationContext()).load(inforUser.getAvatar()).into(avt_profile);
                }
                else {
                    avt_profile.setImageDrawable(getResources().getDrawable(R.drawable.avt_mac_dinh, getTheme()));
                }
                diaChi.setText(inforUser.getAddress().equals(Enum.NULL)?"Chưa cập nhật":inforUser.getAddress());
                nghe.setText(inforUser.getJob().equals(Enum.NULL)?"Chưa cập nhật":inforUser.getJob());
                congTy.setText(inforUser.getCompany().equals(Enum.NULL)?"Chưa cập nhật":inforUser.getCompany());
                soThich.setText(inforUser.getFavorite().equals(Enum.NULL)?"Chưa cập nhật":inforUser.getFavorite());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    private void getDuLieuShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
    }
    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void setFirst(){
        viewPageProfileAdapter = new ViewPageProfileAdapter(this);
        viewPager2.setAdapter(viewPageProfileAdapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case ViewPageProfileAdapter.POST:
                    tab.setText("Bài viết");
                    break;
                case ViewPageProfileAdapter.IMAGE:
                    tab.setText("Ảnh");
                    break;
            }
        }).attach();

    }
    private void setSuKien() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa(){
        ic_back = findViewById(R.id.ic_back);
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        progressBar = findViewById(R.id.progessbar);
        name_profile = findViewById(R.id.name_profile);
        avt_profile = findViewById(R.id.avt_profile);
        diaChi = findViewById(R.id.diachi);
        nghe = findViewById(R.id.nghe);
        congTy = findViewById(R.id.congty);
        soThich = findViewById(R.id.sothich);
    }
}