package com.example.technosocialapp.fragment.friend;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.ProfileFriendActivity;
import com.example.technosocialapp.adapter.ViewInviteAdapter;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.Friend;
import com.example.technosocialapp.model.InforUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListInviteFragment extends AbstractFragment{
    private ArrayList<AuthenticationAdd> arrayList;
    private RecyclerView recyclerView;
    private ViewInviteAdapter adapter;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private long idUser  = -1;
    SharedPreferences sharedPreferences;
    Intent intent;
    InforUser inforUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_list_invite, container, false);
        sharedPreferences = getContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa(view);
        turnOnOrOffProgessBar(true);
        getDataFromShared();
        setFirst();
        setDuLieu();
        setSuKien();

        return view;
    }
    private void setSuKien(){
        adapter.setOnClickItemListener(new ViewInviteAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,arrayList.get(position).getIdSender()).commit();
                goToProfile(arrayList.get(position).getIdSender());
            }

            @Override
            public void onClickXacNhan(int position, View v) {
                xacNhanKetBan(arrayList.get(position).getIdSender());
            }

            @Override
            public void onClickRemove(int position, View v) {
                goKetBan(arrayList.get(position).getIdSender());
            }
        });
    }
    private void goKetBan(long idFriend){
        turnOnOrOffProgessBar(true);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(idUser+"").child(idFriend+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                thongBao("Đã xóa lời mời!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                thongBao("Lỗi!");
                            }
                        });
                        turnOnOrOffProgessBar(false);
                    }
                },
                3000);
    }
    private void xacNhanKetBan(long idFriend){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        long date = tsTemp.getTime();
        turnOnOrOffProgessBar(true);
        Friend friend = new Friend(idFriend,true,date,Enum.RELATIONSHIP_BANBE,false);
        Friend newfriend = new Friend(idUser,true,date,Enum.RELATIONSHIP_BANBE,false);
        databaseReference.child(Enum.FRIEND_TABLE).child(idUser+"").child(idFriend+"").setValue(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        databaseReference.child(Enum.FRIEND_TABLE).child(idFriend+"").child(idUser+"").setValue(newfriend).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(idUser+"").child(idFriend+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                thongBao("Đã thêm bạn mới!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                thongBao("Lỗi!");
                            }
                        });
                        turnOnOrOffProgessBar(false);
                    }
                },
                3000);

    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void goToProfile(long id){
        databaseReference.child(Enum.INFOR_USER_TABLE).child(id+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inforUser = snapshot.getValue(InforUser.class);
                intent.putExtra("infor_friend",inforUser);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getDataFromShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
    }
    private void setDuLieu(){
        adapter = new ViewInviteAdapter(getActivity(),R.layout.card_view_invite,arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        databaseReference.child(Enum.AUTHENTICATION_ADD_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                if (snapshot.getChildrenCount()!=0){
                    for (DataSnapshot snap:
                            snapshot.getChildren()) {
                        AuthenticationAdd authenticationAdd = snap.getValue(AuthenticationAdd.class);
                        arrayList.add(authenticationAdd);
                    }
                }
                turnOnOrOffProgessBar(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void turnOnOrOffProgessBar(boolean check){
        if(check){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setFirst(){
        intent = new Intent(getActivity(),ProfileFriendActivity.class);
        inforUser = new InforUser();
        databaseReference = Enum.DATABASE_REFERENCE;
        arrayList = new ArrayList<>();
    }
    private void anhXa(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewDoc);
        progressBar = fragment.findViewById(R.id.progessbar);
    }
}
