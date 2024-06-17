package com.example.technosocialapp.fragment.friend;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.ProfileFriendActivity;
import com.example.technosocialapp.adapter.ViewFriendAdapter;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.Friend;
import com.example.technosocialapp.model.InforUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFriendFragment extends AbstractFragment{

    private ArrayList<Friend> arrayListFriend;
    private ArrayList<InforUser> arrayListInfor;
    private RecyclerView recyclerView;
    private ViewFriendAdapter adapter;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private long idUser = -1;
    SharedPreferences sharedPreferences;
    private Intent intent;
    private InforUser inforUser;
    private Friend friend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_list_friend, container, false);
        sharedPreferences = getContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa(view);
        turnOnOrOffProgessBar(true);
        getDataFromShared();
        setFirst();
        setDuLieu();
        setSuKienClick();
        return view;
    }
    private void setSuKienClick(){
        adapter.setOnClickItemListener(new ViewFriendAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                inforUser = arrayListInfor.get(position);
                sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,inforUser.getId()).commit();
                goToProfile();
            }
        });
        adapter.setOnClickLongItemListener(new ViewFriendAdapter.OnClickLongItemListener() {
            @Override
            public void onClickLongItem(int position, View v) {
                suKienGoBanBe(arrayListInfor.get(position));


            }
        });
    }
    private void suKienGoBanBe(InforUser inforFriend){
        turnOnOrOffProgessBar(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Bạn Bè");
        builder.setMessage("Bạn thật sự muốn tạm biệt "+inforFriend.getName()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    databaseReference.child(Enum.FRIEND_TABLE).child(idUser+"").child(inforFriend.getId()+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                databaseReference.child(Enum.FRIEND_TABLE).child(inforFriend.getId()+"").child(idUser+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                            thongBao("Đã xóa!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
        ;
        builder.create();
        builder.show();
    }

    private void goToProfile(){
        intent.putExtra("infor_friend",inforUser);
        startActivity(intent);
    }
    private void setDuLieu(){
        adapter = new ViewFriendAdapter(getActivity(),R.layout.card_view_friend,arrayListInfor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        databaseReference.child(Enum.FRIEND_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListFriend.clear();
                arrayListInfor.clear();
                if (snapshot.getChildrenCount()!=0){
                    for (DataSnapshot snap:
                            snapshot.getChildren()) {
                        Friend friend = snap.getValue(Friend.class);
                        if(friend.getType()==1){
                            arrayListFriend.add(friend);
                        }
                    }
                }

                if(arrayListFriend.size()>0){
                    for (int i = 0; i < arrayListFriend.size(); i++) {

                            databaseReference.child(Enum.INFOR_USER_TABLE).child(arrayListFriend.get(i).getIdFriend()+"").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    InforUser inforUser = snapshot.getValue(InforUser.class);
                                    arrayListInfor.add(inforUser);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    }

                }
                adapter.notifyDataSetChanged();
                turnOnOrOffProgessBar(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    private void getDataFromShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
    }
    private void turnOnOrOffProgessBar(boolean check){
        if(check){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void setFirst(){
        intent = new Intent(getActivity(), ProfileFriendActivity.class);
        databaseReference = Enum.DATABASE_REFERENCE;
        arrayListFriend = new ArrayList<>();
        arrayListInfor = new ArrayList<>();
    }
    private void anhXa(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewDoc);
        progressBar = fragment.findViewById(R.id.progessbar);
    }
}
