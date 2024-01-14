package com.example.technosocialapp.activity.dangky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DangKyPart3Activity extends AppCompatActivity {
    ImageView ic_back;
    Button btnXacNhan;
    ConstraintLayout bg_login;
    ProgressBar progressBar;
    Intent intent;

    EditText email;
    EditText password;
    EditText confirmPassword;
    CheckBox checkBox;
    TextView dieuKhoan;
    int type = 0;
    DatabaseReference databaseReference;
    String email_dk = "";
    String password_dk = "";
    boolean authentication_dk = false;
    int type_dk = 1;
    long dateCreate_dk = 0;
    long date = 0;
    String avatar_dk = "";
    String name_dk = "";
    String sex_dk = "";
    String address = "";
    String job_dk = "";
    String company_dk = "";
    String favorite_dk = "";
    int kt = 0;
    long idNew = 0;
    ImageView show1;
    boolean isChecked1 = true;
    ImageView show2;
    boolean isChecked2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_part3);
        intent = new Intent(this, DangKyPart4Activity.class);
        getIntentBanDau();
        anhXa();
        thietLapFireBase();
        setSuKien();
    }
    private void getIntentBanDau(){
        name_dk = getIntent().getStringExtra("name_user");
        sex_dk = getIntent().getStringExtra("sex_user");
        date = getIntent().getLongExtra("date_user",-1);
        address = getIntent().getStringExtra("address_user");
        job_dk = getIntent().getStringExtra("job_user");
        company_dk = getIntent().getStringExtra("company_user");
        favorite_dk = getIntent().getStringExtra("favorite_user");
    }
    private void go(){
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
    }
    private void setSuKien(){
        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked1 == true){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked1 = false;
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked1 = true;
                }
            }
        });
        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked2 == true){
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked2 = false;
                }
                else{
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked2 = true;
                }
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar(true);
                kiemTraRong();
                ketQua();
            }
        });
    }
    private void checkFirebase(){
        databaseReference.child(Enum.USER_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(kt == 0){
                    idNew = snapshot.getChildrenCount();
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        User user = snap.getValue(User.class);
                        if (user.getEmail().equals(email.getText().toString())){
                            kt = 1;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progessBar(false);
                thongBao("Hệ thống đang lỗi!");
            }
        });
    }
    private void dangKyTaiKhoan(long id){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        dateCreate_dk = tsTemp.getTime();
        User user = new User(id,email_dk,password_dk,authentication_dk,type_dk,dateCreate_dk);
        InforUser inforUser = new InforUser(id,avatar_dk,name_dk,date,sex_dk,address,job_dk,company_dk,favorite_dk);
        databaseReference.child(Enum.USER_TABLE).child(id+"").setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dangKyInfor(inforUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progessBar(false);
                thongBao("Tạo tài khoản thất bại!");
            }
        });
    }
    private void dangKyInfor(InforUser inforUser){
       databaseReference.child(Enum.INFOR_USER_TABLE).child(inforUser.getId()+"").setValue(inforUser).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               progessBar(false);
               intent.putExtra("object_user",inforUser);
               go();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               progessBar(false);
               thongBao("Tạo thông tin thất bại, hãy tạo lại tài khoản!");
           }
       });
    }
    private void thietLapFireBase(){

        databaseReference = Enum.DATABASE_REFERENCE;
    }
    private void kiemTraRong(){
        type = 0;
        if(email.getText().toString().trim().isEmpty()){
            type = 1;
            return;
        }
        if (password.getText().toString().trim().isEmpty()){
            type = 2;
            return;
        }
        if (confirmPassword.getText().toString().trim().isEmpty()){
            type = 3;
            return;
        }
        if (checkBox.isChecked()==false){
            type = 4;
            return;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString().trim())){
            type = 5;
            return;
        }
        if (password.getText().toString().trim().length()<8){
            type = 6;
            return;
        }
    }
    private void ketQua(){
        if(type == 0){
            email_dk = email.getText().toString().trim();
            password_dk = password.getText().toString().trim();
            checkFirebase();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                           if(kt == 1){
                               progessBar(false);
                               thongBao("Email đã được đăng ký!");
                               kt = 0;
                           }else {
                                dangKyTaiKhoan(idNew);
                           }
                        }
                    },
                    3000);
        }
        else if(type == 1){
            progessBar(false);
            thongBao("Hãy nhập email để đăng nhập nha!");
        }
        else if(type == 2){
            progessBar(false);
            thongBao("Hãy nhập mật khẩu để đăng nhập nha!");
        }
        else if(type == 3){
            progessBar(false);
            thongBao("Hãy xác nhận lại mật khẩu đã nhập!");
        }
        else if(type == 4){
            progessBar(false);
            thongBao("Đồng ý với điều khoản của ứng dụng thì mới có thể đăng ký!");
        }
        else if(type == 5){
            progessBar(false);
            thongBao("Mật khẩu xác nhận không giống với mật khẩu trên!");
        }
        else if(type == 6){
            progessBar(false);
            thongBao("Mật khẩu tối thiểu 8 kí tự!");
        }

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
        ic_back = findViewById(R.id.ic_back);
        bg_login = findViewById(R.id.bg_login);
        progressBar = findViewById(R.id.progessbar);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_edt);
        confirmPassword = findViewById(R.id.password_cf_edt);
        checkBox = findViewById(R.id.checkBox);
        dieuKhoan = findViewById(R.id.dieuKhoan);
        show1 = findViewById(R.id.show);
        show2 = findViewById(R.id.show_cf);
    }

}