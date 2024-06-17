package com.example.technosocialapp.adapter.album;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.model.ImageBackground;
import com.example.technosocialapp.model.ImagePost;

import java.util.ArrayList;

public class ViewImagePostAdapter extends RecyclerView.Adapter<ViewImagePostAdapter.MyViewHolder>{
    private int layoutId;
    private ArrayList<ImagePost> arrayList;
    private Activity activity;

    public ViewImagePostAdapter(int layoutId, ArrayList<ImagePost> arrayList, Activity activity) {
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
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
