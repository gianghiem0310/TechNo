package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.Story;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewStoryAdapter extends RecyclerView.Adapter<ViewStoryAdapter.MyViewHolder>{
    private int layoutId;
    private Activity activity;
    private ArrayList<Story> arrayList;

    public ViewStoryAdapter(int layoutId, Activity activity, ArrayList<Story> arrayList) {
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
        Story story = arrayList.get(position);
//        Glide.with(activity.getLayoutInflater().getContext()).load(story.getImage()).into(holder.imageView);
//        Glide.with(activity.getLayoutInflater().getContext()).load(story.getImage()).into(holder.circleImageView);
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
        CircleImageView circleImageView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.avt_story);
            imageView = itemView.findViewById(R.id.image_story);
        }
    }
}
