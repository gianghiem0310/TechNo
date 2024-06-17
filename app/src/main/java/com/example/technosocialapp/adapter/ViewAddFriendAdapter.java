package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.InforUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAddFriendAdapter extends RecyclerView.Adapter<ViewAddFriendAdapter.MyViewHolder>{
    private int layoutId;
    private Activity activity;
    private ArrayList<InforUser> arrayList;
    OnClickItemListener onClickItemListener;
    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ViewAddFriendAdapter(int layoutId, Activity activity, ArrayList<InforUser> arrayList) {
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
        InforUser infor = arrayList.get(position);
        holder.name.setText(infor.getName());
        if(!infor.getAvatar().equals(Enum.NULL)) {
            Glide.with(activity).load(infor.getAvatar()).into(holder.avt);
        }
        else {
            holder.avt.setImageDrawable(activity.getResources().getDrawable(R.drawable.avt_mac_dinh, activity.getTheme()));
        }
        if(!infor.getJob().equals(Enum.NULL)){
            holder.job.setText(infor.getJob());
        }else{
            holder.job.setText("Chưa cập nhật ngành");
        }
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

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView avt;
        TextView name;
        TextView job;
        ImageView add;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.avt);
            name = itemView.findViewById(R.id.name);
            job = itemView.findViewById(R.id.job);
            add = itemView.findViewById(R.id.add);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
