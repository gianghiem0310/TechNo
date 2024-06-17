package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.ImagePost;
import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.Story;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPostAdapter extends RecyclerView.Adapter<ViewPostAdapter.MyViewHolder>{
    private int layoutId;
    private Activity activity;
    private ArrayList<Post> arrayList;
    SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference = Enum.DATABASE_REFERENCE;
    OnClickItemListener onClickItemListener;
    public interface OnClickItemListener{
        void onClickItem(int position, View v);
        void onClickLike(int position, View v);
        void onClickComment(int position, View v);
    }
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ViewPostAdapter(int layoutId, Activity activity, ArrayList<Post> arrayList) {
        this.layoutId = layoutId;
        this.activity = activity;
        this.arrayList = arrayList;
        this.sharedPreferences =  activity.getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(viewType, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        //Đổ dữ liệu
//        holder.constraintLayout.removeView(holder.image_post);
        if (position % 2 == 0) {
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(holder.constraintLayout);
//            constraintSet.connect(R.id.info_post,ConstraintSet.TOP,R.id.body_post,ConstraintSet.BOTTOM);
//            constraintSet.applyTo(holder.constraintLayout);
        }
        Post post = arrayList.get(position);
        holder.content.setText(post.getContent());
        databaseReference = Enum.DATABASE_REFERENCE;
        databaseReference.child(Enum.IMAGE_POST_TABLE).child(post.getId() + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(ImagePost.class) != null) {
                    Glide.with(activity.getLayoutInflater().getContext()).load(snapshot.getValue(ImagePost.class).getImage()).into(holder.imageView);
                }
                else {
                    holder.constraintLayout.removeView(holder.image_post);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(holder.constraintLayout);
                    constraintSet.connect(R.id.info_post,ConstraintSet.TOP,R.id.body_post,ConstraintSet.BOTTOM);
                    constraintSet.applyTo(holder.constraintLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser()+"").child(post.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>1000){
                    holder.number_like.setText("Hơn 1K");
                }else{
                    holder.number_like.setText(snapshot.getChildrenCount()+" Lượt Thích");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(Enum.LIKE_TABLE).child(post.getIdInforUser()+"").child(post.getId()+"").child(sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT)+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    holder.like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_heart_red,activity.getTheme()));
                }else{
                    holder.like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_heart_white,activity.getTheme()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.name_post:
                        onClickItemListener.onClickItem(position, v);
                        break;
                    case R.id.like:

                        onClickItemListener.onClickLike(position,v);
                        break;
                    case R.id.comment:

                        onClickItemListener.onClickComment(position,v);
                        break;
                }


            }
        };
        databaseReference.child(Enum.INFOR_USER_TABLE).child(post.getIdInforUser()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    InforUser inforUser = snapshot.getValue(InforUser.class);
                    holder.name.setText(inforUser.getName());
                    if(!inforUser.getAvatar().equals(Enum.NULL)){
                        Glide.with(activity.getLayoutInflater().getContext()).load(inforUser.getAvatar()).into(holder.avt_post);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//    }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ConstraintLayout constraintLayout;
        ConstraintLayout image_post;
        ConstraintLayout infor_post;
        TextView body_post;
        ImageView imageView;
        TextView name;
        CircleImageView avt_post;
        TextView content;
        View.OnClickListener onClickListener;
        ImageView like;

        ImageView comment;
        TextView number_like;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.view_boss);
            image_post = itemView.findViewById(R.id.image_post);
            infor_post = itemView.findViewById(R.id.info_post);
            body_post = itemView.findViewById(R.id.body_post);
            imageView = itemView.findViewById(R.id.img_post);
            name = itemView.findViewById(R.id.name_post);
            avt_post = itemView.findViewById(R.id.avt_post);
            content = itemView.findViewById(R.id.body_post);
            number_like = itemView.findViewById(R.id.number_like);
            name.setOnClickListener(this);
            like = itemView.findViewById(R.id.like);

            comment = itemView.findViewById(R.id.comment);

            like.setOnClickListener(this);

            comment.setOnClickListener(this);


        }
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
