package com.example.technosocialapp.adapter.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.model.InforUser;

import java.util.ArrayList;

public class ViewOnlineApdater extends RecyclerView.Adapter<ViewOnlineApdater.MyViewHolder>{
    private int layoutId;
    private ArrayList<InforUser> inforUsers;
    private Activity activity;

    public ViewOnlineApdater(int layoutId, ArrayList<InforUser> inforUsers, Activity activity) {
        this.layoutId = layoutId;
        this.inforUsers = inforUsers;
        this.activity = activity;
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

    }

    @Override
    public int getItemCount() {
        return inforUsers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
