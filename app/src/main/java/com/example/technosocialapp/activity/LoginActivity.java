package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;

import com.example.technosocialapp.activity.dangky.DangKyPart1Activity;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnTiepTuc;
    ImageView ic_back;
    EditText edtEmail;
    EditText edtPassword;
    TextView btnDangKy;
    DatabaseReference databaseReference;
    int type = 0;
    SharedPreferences sharedPreferences;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;
    ImageView show;
    boolean isChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa();
        thietLapFireBase();
        setSuKien();
        setFirst();
    }
    private void setFirst(){
        btnTiepTuc.setEnabled(false);
        btnTiepTuc.setBackgroundResource(R.drawable.background_btn_false);
    }
    private void thietLapFireBase(){
        databaseReference = Enum.DATABASE_REFERENCE;
    }
    private void setSuKien(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked == true){
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked = false;
                }
              else{
                  edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked = true;
                }
            }
        });

        if(sharedPreferences.getInt(Enum.CHECK_USED,0)==Enum.USED){
            ic_back.setVisibility(View.INVISIBLE);
        }
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                btnTiepTuc.setEnabled(true);
                btnTiepTuc.setBackgroundResource(R.drawable.background_btn);}
                else{
                    btnTiepTuc.setEnabled(false);
                    btnTiepTuc.setBackgroundResource(R.drawable.background_btn_false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar(true);
                kiemTra();
                ketQua();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDangKi();
            }
        });
    }
    private void ketQua(){
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        if(type==0){
                            progessBar(false);
                            go();
                        }else if(type==1){
                            progessBar(false);
                            thongBao("Email hoặc mật khẩu bị trống!");
                        }else if(type == 2){
                            progessBar(false);
                            thongBao("Email hoặc mật khẩu không đúng!");
                        }else if(type == 3){
                            progessBar(false);
                            thongBao("Kết nối mạng chậm!");
                        }
                    }
                },
                3000);
    }
    private void go(){
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);

    }
    private void goDangKi(){
        Intent intent = new Intent(this, DangKyPart1Activity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }
    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            constraintLayout.setVisibility(View.INVISIBLE);
        }
    }
    private void kiemTra(){
        type = 0;
        if(edtEmail.getText().toString().isEmpty()||edtPassword.getText().toString().isEmpty()){
            type = 1;
            return;
        }
        databaseReference.child(Enum.USER_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()) {
                    User user = item.getValue(User.class);
                    if(edtEmail.getText().toString().equals(user.getEmail())&&edtPassword.getText().toString().equals(user.getPassword())){
                        getInforUser(user.getId());
                    }else{
                        type = 2;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                type = 3;
            }
        });
    }
    private void getInforUser(long id){
        databaseReference.child(Enum.INFOR_USER_TABLE).child(id+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InforUser user = snapshot.getValue(InforUser.class);
                setShared(user.getId(),user.getName(),user.getAvatar());
                type = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                type = 2;
            }
        });
    }
    private void setShared(long id, String name, String avatar){
        sharedPreferences.edit().putLong(Enum.ID_USER,id).commit();
        sharedPreferences.edit().putString(Enum.NAME_USER,name).commit();
        sharedPreferences.edit().putString(Enum.IMAGE_USER,avatar).commit();
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
        btnTiepTuc = findViewById(R.id.btnDangNhap);
        ic_back = findViewById(R.id.ic_back);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password_edt);
        constraintLayout = findViewById(R.id.bg_login);
        progressBar = findViewById(R.id.progessbar);
        btnDangKy = findViewById(R.id.btnDangKy);
        show = findViewById(R.id.show);
    }
}