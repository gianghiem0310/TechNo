package com.example.technosocialapp.adapter.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.model.InforUser;
import com.example.technosocialapp.model.Message;

import java.util.ArrayList;

public class ViewMessageApdater extends RecyclerView.Adapter<ViewMessageApdater.MyViewHolder>{
    private int layoutIdSent;
    private int layoutIdReceive;
    private ArrayList<Message> arrayList;
    private Activity activity;

    public ViewMessageApdater(int layoutIdSent, int layoutIdReceive, ArrayList<Message> arrayList, Activity activity) {
        this.layoutIdSent = layoutIdSent;
        this.layoutIdReceive = layoutIdReceive;
        this.arrayList = arrayList;
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
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return layoutIdReceive;
        }
        return layoutIdSent;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
