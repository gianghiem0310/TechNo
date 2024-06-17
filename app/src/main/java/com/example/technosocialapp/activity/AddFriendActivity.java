package com.example.technosocialapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewAddFriendAdapter;
import com.example.technosocialapp.model.InforUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    ArrayList<InforUser> arrayList;
    ViewAddFriendAdapter viewAddFriendAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView ic_back;
    private long idUser = -1;
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference = Enum.DATABASE_REFERENCE;
    Intent intent;
    InforUser inforUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        sharedPreferences = getBaseContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        getDuLieuShared();
        anhXa();
        turnOnOrOffProgessBar(true);
        setFirst();
        setData();
        setEvent();
        setEventClickItem();
        turnOnOrOffProgessBar(false);
    }
    private void setEventClickItem(){
        viewAddFriendAdapter.setOnClickItemListener(new ViewAddFriendAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                inforUser = arrayList.get(position);
                sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,inforUser.getId()).commit();
                if(inforUser.getId()==idUser){
                    intent = new Intent(AddFriendActivity.this, ProfileActivity.class);
                }else{
                    intent = new Intent(AddFriendActivity.this, ProfileFriendActivity.class);
                }
                goToProfile();

            }
        });
    }
    private void goToProfile(){
        intent.putExtra("infor_friend",inforUser);
        startActivity(intent);
    }
    private void turnOnOrOffProgessBar(boolean check){
        if(check){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
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
    private void setEvent(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setData(){
        arrayList.clear();
        databaseReference.child(Enum.INFOR_USER_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snap:snapshot.getChildren()
                     ) {
                    InforUser inforUser = snap.getValue(InforUser.class);
                    arrayList.add(inforUser);
                }
                viewAddFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setFirst(){
        arrayList = new ArrayList<>();
        viewAddFriendAdapter = new ViewAddFriendAdapter(R.layout.card_view_add_friend,this,arrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(viewAddFriendAdapter);
        viewAddFriendAdapter.notifyDataSetChanged();
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
    private void anhXa(){
        progressBar = findViewById(R.id.progessbar);
        recyclerView = findViewById(R.id.recyclerViewDoc);
        ic_back = findViewById(R.id.ic_back);
    }








}