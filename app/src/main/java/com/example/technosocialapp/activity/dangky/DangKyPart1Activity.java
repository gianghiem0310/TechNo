package com.example.technosocialapp.activity.dangky;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;

import java.util.ArrayList;
import java.util.Date;

public class DangKyPart1Activity extends AppCompatActivity {

    ImageView ic_back;
    EditText name;
    TextView date;
    Spinner spinner;
    Button btnTiepTuc;
    ConstraintLayout bg_login;
    ProgressBar progressBar;
    Intent intent;
    int type = 0;
    String gioiTinh = Enum.GIOITINH;
    long date_user = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_part1);
        intent = new Intent(this, DangKyPart2Activity.class);
        anhXa();
        setSuKien();
        setDuLieuSpinner();
    }
    private void go(){
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);

    }
    private void setSuKien(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDuLieuDate();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    gioiTinh = Enum.GIOITINH;
                }
                else if(i == 1){
                    gioiTinh = Enum.NAM;
                }else if(i==2){
                    gioiTinh = Enum.NU;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar(true);
                kiemTraRong();
                ketQua();
            }
        });
    }
    private void setIntent(String name,long date, String gioiTinh){
        intent.putExtra("name_user",name);
        intent.putExtra("date_user",date);
        intent.putExtra("sex_user",gioiTinh);
    }
    private void ketQua(){
        progessBar(false);
        if(type == 0){
            setIntent(name.getText().toString(),date_user,gioiTinh);
            go();
        }else if(type == 1){
            thongBao("Hãy nhập tên cho bản thân nhé!");
        }
        else if(type == 2){
            thongBao("Hãy chọn ngày sinh cho bản thân nhé!");
        }
        else if(type == 3){
            thongBao("Hãy chọn giới tính cho bản thân nhé!");
        }
    }
    private void kiemTraRong(){
       type = 0;
        if(name.getText().toString().isEmpty()){
            type = 1;
            return;
        }
        if(date.getText().toString().isEmpty()){
            type = 2;
            return;
        }
        if(gioiTinh==Enum.GIOITINH){
            type = 3;
            return;
        }
    }
    private void setDuLieuDate(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date.setText(i2+"/"+i1+"/"+i);
                Date newDate = new Date( i, i1 - 1, i2);
                date_user = newDate.getTime();
            }
        },2023,01,01);
        dialog.show();
    }

    private void setDuLieuSpinner(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("Giới tính");
        arrayList.add("Nam");
        arrayList.add("Nữ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(adapter);
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
        name = findViewById(R.id.email);
        date = findViewById(R.id.password);
        spinner = findViewById(R.id.spinner);
        bg_login = findViewById(R.id.bg_login);
        progressBar = findViewById(R.id.progessbar);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
    }
}