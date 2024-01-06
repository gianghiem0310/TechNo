package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;

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
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int type = 0;
    SharedPreferences sharedPreferences;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void setSuKien(){
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
                kiemTra();
                ketQua();
            }
        });
    }
    private void ketQua(){
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        if(type==0){
                            go();
                        }else if(type==1){
                            thongBao("Email hoặc mật khẩu bị trống!");
                        }else if(type == 2){
                            thongBao("Email hoặc mật khẩu không đúng!");
                        }else if(type == 3){
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
    private void getInforUser(int id){
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
    private void setShared(int id, String name, String avatar){
        sharedPreferences.edit().putString(Enum.ID_USER,id+"").commit();
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
        edtPassword = findViewById(R.id.password);
    }
}