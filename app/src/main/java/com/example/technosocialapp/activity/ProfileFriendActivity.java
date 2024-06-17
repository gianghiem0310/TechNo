package com.example.technosocialapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewPageProfileAdapter;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.Conversation;
import com.example.technosocialapp.model.Friend;
import com.example.technosocialapp.model.InforUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFriendActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ic_back;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ViewPageProfileAdapter viewPageProfileAdapter;
    SharedPreferences sharedPreferences;
    private long idUser = -1;
    private String nameUser = Enum.NULL;
    private String avatarUser = Enum.NULL;
    private Intent intent;
    private InforUser inforUser;

    ProgressBar progressBar;
    TextView name_profile;

    CircleImageView avt_profile;
    TextView diaChi;
    TextView nghe;
    TextView congTy;
    TextView soThich;
    Button btnAdd;
    Button btnMessage;
    ConstraintLayout view_profile;
    int status = 0;
    boolean created = false;
    boolean buocTao = false;

    private DatabaseReference databaseReference = Enum.DATABASE_REFERENCE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_friend);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        getDataFromShared();
        anhXa();
        turnOnOrOffProgessBar(true);
        view_profile.setEnabled(false);
        setSuKien();
        setFirst();
        getDataFromIntent();
        setDataForButtonAdd();
        setDataForProfile();
        setEventForButton();

    }
    private void eventAddFriend(){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        long date = tsTemp.getTime();
        AuthenticationAdd authenticationAdd = new AuthenticationAdd(idUser,sharedPreferences.getString(Enum.IMAGE_USER,Enum.NULL),sharedPreferences.getString(Enum.NAME_USER,Enum.NULL),date,false);
        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(inforUser.getId()+"").child(idUser+"").setValue(authenticationAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongBao("Lỗi gửi lời kết bạn!");
            }
        });
    }
    private void eventBanBe(){

    }
    private void eventFollowing(){

    }
    private void eventDaGui(){

    }
    private void eventDongY(){

    }
    private void setEventForButton(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (status){
                    case Enum.RELATIONSHIP_ADDFRIEND:
                        eventAddFriend();
                        break;
                    case Enum.RELATIONSHIP_BANBE:
                        eventBanBe();
                        break;
                    case Enum.RELATIONSHIP_FOLLOWING:
                        eventFollowing();
                        break;
                    case Enum.RELATIONSHIP_DAGUI:
                        eventDaGui();
                        break;
                    case Enum.RELATIONSHIP_DONGY:
                        eventDongY();
                        break;
                    default:
                        eventBanBe();
                        break;
                }
            }
        });

    }
    private void setDataForButtonAdd(){
        databaseReference.child(Enum.FRIEND_TABLE).child(idUser+"").child(inforUser.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Friend.class)==null){
                 btnAdd.setText("Kết bạn");
                 status = Enum.RELATIONSHIP_ADDFRIEND;
                }
                else{
                    Friend friend = snapshot.getValue(Friend.class);
                    if(friend.getType()==1){
                        btnAdd.setText("Bạn bè");
                        status = Enum.RELATIONSHIP_BANBE;
                    }
                    else
                    {
                        btnAdd.setText("Đang follow");
                        status = Enum.RELATIONSHIP_FOLLOWING;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                thongBao("Lỗi hệ thống!");
            }
        });
        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(inforUser.getId()+"").child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(AuthenticationAdd.class)!=null){
                    btnAdd.setText("Đã gửi");
                    status = Enum.RELATIONSHIP_DAGUI;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(idUser+"").child(inforUser.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(AuthenticationAdd.class)!=null){
                    btnAdd.setText("Đồng ý");
                    status = Enum.RELATIONSHIP_DONGY;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataForProfile() {
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

    private void getDataFromShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
        nameUser = sharedPreferences.getString(Enum.NAME_USER,Enum.NULL);
        avatarUser = sharedPreferences.getString(Enum.IMAGE_USER,Enum.NULL);
    }
    private void getDataFromIntent(){
        inforUser = (InforUser) intent.getSerializableExtra("infor_friend");
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
    @Override
    protected void onResume() {
        super.onResume();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        turnOnOrOffProgessBar(false);
                        view_profile.setEnabled(true);
                    }
                },
                3000);
        Enum.turnOnAndOffStatus(true,idUser );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Enum.turnOnAndOffStatus(false,idUser);
    }
    private void setFirst(){
        intent = getIntent();
        inforUser = new InforUser();
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
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buocTao = true;
                setSuKienNhanTin();
            }
        });
    }
    private void setSuKienNhanTin(){
        kiemTraTonTai();
    }
    private void kiemTraTonTai(){
        if(buocTao == true) {
            databaseReference.child(Enum.CONVERSATION_TABLE).child(idUser + "").child(inforUser.getId() + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                        created = false;
                    } else {
                        created = true;
                    }
                    buocTao = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {

                        if(created==false){
                            taoDoanHoiThoaiMoi();
                            goToChatScreen();
                        }else{
                            goToChatScreen();
                        }
                        }},3000);
        }
    }
    private void goToChatScreen(){
        intent = new Intent(ProfileFriendActivity.this, ChatScreenActivity.class);
        startActivity(intent);
    }
    private void taoDoanHoiThoaiMoi(){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        long time = tsTemp.getTime();
        Conversation conversation = new Conversation(inforUser.getId(),inforUser.getName(),inforUser.getAvatar(),time,Enum.NULL);
        databaseReference.child(Enum.CONVERSATION_TABLE).child(idUser+"").child(inforUser.getId()+"").setValue(conversation);
        Conversation conversationOf = new Conversation(idUser,nameUser,avatarUser,time,Enum.NULL);
        databaseReference.child(Enum.CONVERSATION_TABLE).child(inforUser.getId()+"").child(idUser+"").setValue(conversationOf);
    }

    private void turnOnOrOffProgessBar(boolean check){
        if(check){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
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
        btnAdd = findViewById(R.id.btn_add);
        btnMessage = findViewById(R.id.btn_message);
        view_profile = findViewById(R.id.view_profile);
    }
}