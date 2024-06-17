package com.example.technosocialapp.adapter.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.adapter.ViewFriendAdapter;
import com.example.technosocialapp.model.Chat;

import java.util.ArrayList;

public class ViewChatApdater extends RecyclerView.Adapter<ViewChatApdater.MyViewHolder>{
    private int layoutId;
    private ArrayList<Chat> arrayList;
    private Activity activity;
    OnClickItemListener onClickItemListener;
    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }
    public ViewChatApdater(int layoutId, ArrayList<Chat> arrayList, Activity activity) {
        this.layoutId = layoutId;
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
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemListener.onClickItem(position,view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
