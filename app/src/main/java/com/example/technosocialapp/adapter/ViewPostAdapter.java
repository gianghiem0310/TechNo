package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.media.Image;
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

import com.example.technosocialapp.R;
import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.Story;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPostAdapter extends RecyclerView.Adapter<ViewPostAdapter.MyViewHolder>{
    private int layoutId;
    private Activity activity;
    private ArrayList<Post> arrayList;

    public ViewPostAdapter(int layoutId, Activity activity, ArrayList<Post> arrayList) {
        this.layoutId = layoutId;
        this.activity = activity;
        this.arrayList = arrayList;
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
        if (position % 2 == 0){
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(holder.constraintLayout);
//            constraintSet.connect(R.id.info_post,ConstraintSet.TOP,R.id.body_post,ConstraintSet.BOTTOM);
//            constraintSet.applyTo(holder.constraintLayout);
            holder.imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.post1, activity.getTheme()));

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ConstraintLayout image_post;
        ConstraintLayout infor_post;
        TextView body_post;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.view_boss);
            image_post = itemView.findViewById(R.id.image_post);
            infor_post = itemView.findViewById(R.id.info_post);
            body_post = itemView.findViewById(R.id.body_post);
            imageView = itemView.findViewById(R.id.img_post);
        }
    }
}
