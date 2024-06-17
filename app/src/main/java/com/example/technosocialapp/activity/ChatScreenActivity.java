package com.example.technosocialapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.chat.ViewMessageApdater;
import com.example.technosocialapp.adapter.chat.ViewOnlineApdater;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.Message;

import java.util.ArrayList;

public class ChatScreenActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView ic_back;
    ArrayList<Message> arrayList;
    ViewMessageApdater viewMessageApdater;
    RecyclerView recyclerViewDoc;
    ImageView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        anhXa();
        setFirst();
        setEvent();
        setData();
    }
    private void turnOnOrOffProgessBar(boolean check){
        if(check){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
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
    private void setEvent(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                thongBao("OK");
                return false;
            }
        });
    }
    private void setData(){
        arrayList.clear();
        viewMessageApdater.notifyDataSetChanged();
        if(arrayList.size()>0){
            recyclerViewDoc.smoothScrollToPosition(arrayList.size()-1);
        }

    }
    private void getDataFromIntent(){

    }
    private void getDataFromShared(){

    }
    private void setFirst(){
        arrayList = new ArrayList<>();
        viewMessageApdater = new ViewMessageApdater(R.layout.cardview_send_message,R.layout.cardview_recieve_message,arrayList,this);
        recyclerViewDoc.setAdapter(viewMessageApdater);
    }
    private void anhXa(){
        progressBar = findViewById(R.id.progessbar);
        ic_back = findViewById(R.id.ic_back);
        recyclerViewDoc = findViewById(R.id.recyclerViewDoc);
        send = findViewById(R.id.send);
    }
}