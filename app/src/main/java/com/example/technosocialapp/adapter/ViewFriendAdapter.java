package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.Friend;
import com.example.technosocialapp.model.InforUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewFriendAdapter extends RecyclerView.Adapter<ViewFriendAdapter.MyViewHolder> {

    private Activity activity;
    private int layoutId;
    private ArrayList<InforUser> arrayList;
    OnClickItemListener onClickItemListener;
    OnClickLongItemListener onClickLongItemListener;
    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }
    public interface OnClickLongItemListener{
        void onClickLongItem(int position, View v);
    }
    public void setOnClickLongItemListener(OnClickLongItemListener onClickLongItemListener) {
        this.onClickLongItemListener = onClickLongItemListener;
    }
    public ViewFriendAdapter(Activity activity, int layoutId, ArrayList<InforUser> arrayList) {
        this.activity = activity;
        this.layoutId = layoutId;
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
        holder.name.setText(infor.getName());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(position, v);
            }
        };
        holder.onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickLongItemListener.onClickLongItem(position,view);
                return false;
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

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        View.OnClickListener onClickListener;
        View.OnLongClickListener onLongClickListener;
        CircleImageView avt;
        TextView name;
        TextView job;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            avt = itemView.findViewById(R.id.avt_friend);
            name = itemView.findViewById(R.id.name_friend);
            job = itemView.findViewById(R.id.job_friend);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }

        @Override
        public boolean onLongClick(View view) {
            onLongClickListener.onLongClick(view);
            return false;
        }
    }
}
