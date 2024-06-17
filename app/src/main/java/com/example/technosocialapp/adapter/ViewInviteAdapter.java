package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.InforUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewInviteAdapter extends RecyclerView.Adapter<ViewInviteAdapter.MyViewHolder> {

    private Activity activity;
    private int layoutId;
    private ArrayList<AuthenticationAdd> arrayList;
    OnClickItemListener onClickItemListener;
    public interface OnClickItemListener{
        void onClickItem(int position, View v);
        void onClickXacNhan(int position, View v);
        void onClickRemove(int position, View v);
    }

    public ViewInviteAdapter(Activity activity, int layoutId, ArrayList<AuthenticationAdd> arrayList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
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
        AuthenticationAdd infor = arrayList.get(position);
        holder.name.setText(infor.getName());
        if(!infor.getAvatar().equals(Enum.NULL)) {
            Glide.with(activity).load(infor.getAvatar()).into(holder.avt);
        }
        else {
            holder.avt.setImageDrawable(activity.getResources().getDrawable(R.drawable.avt_mac_dinh, activity.getTheme()));
        }
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.name_friend:
                        onClickItemListener.onClickItem(position, v);
                        break;
                    case R.id.btnXacNhan:
                        onClickItemListener.onClickXacNhan(position,v);
                        break;
                    case R.id.btnGo:
                        onClickItemListener.onClickRemove(position,v);
                        break;
                }

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
        View.OnClickListener onClickListener;
        CircleImageView avt;
        TextView name;
        TextView time;
        Button btnDongY;
        Button btnGo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.avt_friend);
            name = itemView.findViewById(R.id.name_friend);
            time = itemView.findViewById(R.id.time);
            btnDongY = itemView.findViewById(R.id.btnXacNhan);
            btnGo = itemView.findViewById(R.id.btnGo);
            btnDongY.setOnClickListener(this);
            btnGo.setOnClickListener(this);
            name.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
