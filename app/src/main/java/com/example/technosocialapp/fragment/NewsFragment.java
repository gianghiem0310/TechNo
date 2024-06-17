package com.example.technosocialapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.activity.ProfileActivity;
import com.example.technosocialapp.activity.ProfileFriendActivity;
import com.example.technosocialapp.adapter.ViewPostAdapter;
import com.example.technosocialapp.adapter.ViewStoryAdapter;
import com.example.technosocialapp.model.BigPost;
import com.example.technosocialapp.model.Friend;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.Like;
import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.Story;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFragment extends AbstractFragment{
    private RecyclerView recyclerViewNgang;
    private RecyclerView recyclerViewDoc;
    private ArrayList<Story> listStory;
    private ViewStoryAdapter storyAdapter;
    private ArrayList<Post> listPost;
    private ViewPostAdapter postAdapter;
    private DatabaseReference databaseReference;
    ProgressBar progressBar;
    Intent intent;
    InforUser inforUser;
    SharedPreferences sharedPreferences;
    long idUser= -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_news, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        databaseReference = Enum.DATABASE_REFERENCE;
        getDuLieuShared();
        listStory = new ArrayList<>();
        listPost = new ArrayList<>();
        anhXa(view);
        progessBar(true);
        setDuLieuNgang();
        setDuLieuDoc();
        setEventForListPost();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.title.setText("Tech No");

    }
    private void getDuLieuShared(){
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
    }
    private void goToProfile(){
        intent = new Intent(getActivity(), ProfileFriendActivity.class);
        intent.putExtra("infor_friend",inforUser);
        startActivity(intent);
    }
    private void setEventForListPost(){
       postAdapter.setOnClickItemListener(new ViewPostAdapter.OnClickItemListener() {
           @Override
           public void onClickItem(int position, View v) {
               databaseReference.child(Enum.INFOR_USER_TABLE).child(listPost.get(position).getIdInforUser()+"").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       inforUser = snapshot.getValue(InforUser.class);
                        if(idUser==inforUser.getId()){
                            sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT)).commit();
                            startActivity(new Intent(getActivity(), ProfileActivity.class));
                        }else{
                            sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,inforUser.getId()).commit();
                            goToProfile();
                        }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }

           @Override
           public void onClickLike(int position, View v) {
               ImageView imageView = (ImageView) v;
               Like like = new Like(listPost.get(position).getId(),idUser);
               Post post = listPost.get(position);
                if (imageView.getDrawable().getConstantState()==getResources().getDrawable( R.drawable.ic_heart_white).getConstantState()) {
                    databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser() + "").child(post.getId() + "").child(idUser + "").setValue(like).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                } else if(imageView.getDrawable().getConstantState()==getResources().getDrawable( R.drawable.ic_heart_red).getConstantState()) {
                    databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser() + "").child(post.getId() + "").child(idUser + "").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void setDuLieuNgang(){
        Story story = new Story(1,1,"image","image",11);
        for (int i = 0; i < 6; i++) {
            listStory.add(story);
        }
        storyAdapter = new ViewStoryAdapter(R.layout.card_view_story,getActivity(),listStory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewNgang.setLayoutManager(linearLayoutManager);
        recyclerViewNgang.setAdapter(storyAdapter);
        storyAdapter.notifyDataSetChanged();

    }
//    private void setDuLieuDoc(){
//        listPost.clear();
//        postAdapter = new ViewPostAdapter(R.layout.card_view_post,getActivity(),listPost);
//        databaseReference.child(Enum.POST_TABLE).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listPost.clear();
//
//                if(snapshot.getChildrenCount()!=0){
//                    for (DataSnapshot snap:snapshot.getChildren()) {
//                        for (DataSnapshot snap1:snap.getChildren()) {
//
//                            Post post = snap1.getValue(Post.class);
//
//                                listPost.add(post);
//
//
//                        }
//                    }
//                    listPost = Enum.hamSapXepPost(listPost,false);
//                }
//
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerViewDoc.setLayoutManager(linearLayoutManager);
//                recyclerViewDoc.setAdapter(postAdapter);
//                postAdapter.notifyDataSetChanged();
////                setEventForListPost();
//                progessBar(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }
    private void setDuLieuDoc(){
        listPost.clear();
        postAdapter = new ViewPostAdapter(R.layout.card_view_post,getActivity(),listPost);





        databaseReference.child(Enum.FRIEND_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost.clear();
                databaseReference.child(Enum.POST_TABLE).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getChildrenCount()!=0){
                            for (DataSnapshot snap:snapshot.getChildren()) {
                                for (DataSnapshot snap1:snap.getChildren()) {

                                    Post post = snap1.getValue(Post.class);
                                    if(post.getIdInforUser()==idUser){
                                        listPost.add(post);
                                    }



                                }
                            }
                            listPost = Enum.hamSapXepPost(listPost,false);
                        }


                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerViewDoc.setLayoutManager(linearLayoutManager);
                        recyclerViewDoc.setAdapter(postAdapter);
                        postAdapter.notifyDataSetChanged();
                        progessBar(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(snapshot.getChildrenCount()!=0){
                    for (DataSnapshot snap:snapshot.getChildren()) {
                        Friend friend = snap.getValue(Friend.class);
                            databaseReference.child(Enum.POST_TABLE).child(friend.getIdFriend()+"").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.getChildrenCount()!=0){
                                        for (DataSnapshot snap:snapshot.getChildren()
                                             ) {
                                            Post post = snap.getValue(Post.class);
                                            listPost.add(post);
                                        }
                                        listPost = Enum.hamSapXepPost(listPost,false);
                                    }
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerViewDoc.setLayoutManager(linearLayoutManager);
                                    recyclerViewDoc.setAdapter(postAdapter);
                                    postAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    }
                }
                progessBar(false);
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
    private void anhXa(View frag){
        recyclerViewNgang = frag.findViewById(R.id.recyclerViewNgang);
        recyclerViewDoc = frag.findViewById(R.id.recyclerViewDoc);
        progressBar = frag.findViewById(R.id.progessbar);
    }
}
