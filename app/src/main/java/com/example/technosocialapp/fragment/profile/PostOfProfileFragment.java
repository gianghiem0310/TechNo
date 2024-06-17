package com.example.technosocialapp.fragment.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewPostAdapter;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.Like;
import com.example.technosocialapp.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PostOfProfileFragment extends AbstractFragment{
    private RecyclerView recyclerViewDoc;
    private ArrayList<Post> listPost;
    private ViewPostAdapter postAdapter;
    private DatabaseReference databaseReference;
    private long idUser;
    private SharedPreferences sharedPreferences;
    InforUser inforUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_post_profile, container, false);
        databaseReference = Enum.DATABASE_REFERENCE;
        sharedPreferences = getContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        listPost = new ArrayList<>();
        getDataFromShared();
        anhXa(view);
        setDuLieuDoc();
        setEventForListPost();
        return view;
    }
    private void getDataFromShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER_VIEW,Enum.NULL_INT);
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

    private void setEventForListPost(){
        postAdapter.setOnClickItemListener(new ViewPostAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                thongBao("Đang ở trang người này!");
            }

            @Override
            public void onClickLike(int position, View v) {
                ImageView imageView = (ImageView) v;
                Like like = new Like(listPost.get(position).getId(),idUser);
                Post post = listPost.get(position);
                if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable( R.drawable.ic_heart_white).getConstantState())) {
                    databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser() + "").child(post.getId() + "").child(sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT) + "").setValue(like).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
                else if(imageView.getDrawable().getConstantState().equals(getResources().getDrawable( R.drawable.ic_heart_red).getConstantState())) {
                    databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser() + "").child(post.getId() + "").child(sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT) + "").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
            }

            @Override
            public void onClickComment(int position, View v) {
                thongBao("Binh luan");
            }
        });
    }

    private void setDuLieuDoc(){
        listPost.clear();
        postAdapter = new ViewPostAdapter(R.layout.card_view_post,getActivity(),listPost);
        databaseReference.child(Enum.POST_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost.clear();
                for (DataSnapshot snap:snapshot.getChildren()) {
                    listPost.add(snap.getValue(Post.class));
                }
                listPost = Enum.hamSapXepPost(listPost,false);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewDoc.setLayoutManager(linearLayoutManager);
                recyclerViewDoc.setAdapter(postAdapter);
//                setEventForListPost();
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void anhXa(View frag){
        recyclerViewDoc = frag.findViewById(R.id.recyclerViewDoc);
    }
}
