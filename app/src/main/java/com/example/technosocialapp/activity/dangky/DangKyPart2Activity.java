package com.example.technosocialapp.activity.dangky;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;

public class DangKyPart2Activity extends AppCompatActivity {
    ImageView ic_back;
    Spinner spinner;
    Button btnTiepTuc;
    ConstraintLayout bg_login;
    ProgressBar progressBar;
    Intent intent;

    EditText address;
    String job = Enum.SINHVIEN;
    EditText company;
    EditText favorite;
    TextView skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_part2);
        intent = new Intent(this, DangKyPart3Activity.class);
        getIntentBanDau();
        anhXa();
        setDuLieuSpinner();
        setSuKien();
    }
    private void getIntentBanDau(){
        intent.putExtra("name_user",getIntent().getStringExtra("name_user"));
        intent.putExtra("date_user",getIntent().getLongExtra("date_user",-1));
        intent.putExtra("sex_user",getIntent().getStringExtra("sex_user"));
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
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    job = Enum.SINHVIEN;
                }
                else if(i == 1){
                    job = Enum.CONGNHAN;
                }else if(i==2){
                    job = Enum.DOANHNHAN;
                }else if(i == 3){
                    job = Enum.NHANVIEN;
                }else if(i == 4){
                    job = Enum.GIAOVIEN;
                }else if(i==5){
                    job = Enum.KHAC;
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
                ketQua();
            }
        });
    }
    private void setIntent(String address, String job, String company, String favorite){

        if(!address.trim().isEmpty()){
            intent.putExtra("address_user",address);
        }
        if(!job.isEmpty()){
            intent.putExtra("job_user",job);
        }
        if(!company.trim().isEmpty()){
            intent.putExtra("company_user",company);
        }
        if(!favorite.trim().isEmpty()){
            intent.putExtra("favorite_user",favorite);
        }

    }
    private void ketQua(){
            setIntent(address.getText().toString().trim(),job,company.getText().toString().trim(),favorite.getText().toString().trim());
            progessBar(false);
            go();
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
    private void setDuLieuSpinner(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("Sinh viên");
        arrayList.add("Công nhân");
        arrayList.add("Doanh nhân");
        arrayList.add("Nhân viên");
        arrayList.add("Giáo viên");
        arrayList.add("Khác");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(adapter);
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
        spinner = findViewById(R.id.spinner);
        bg_login = findViewById(R.id.bg_login);
        progressBar = findViewById(R.id.progessbar);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        address = findViewById(R.id.address);
        company = findViewById(R.id.company);
        favorite = findViewById(R.id.favorite);
        skip = findViewById(R.id.skip);
    }
}