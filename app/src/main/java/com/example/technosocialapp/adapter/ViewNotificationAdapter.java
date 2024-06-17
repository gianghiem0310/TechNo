package com.example.technosocialapp.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.Notification;

import java.util.ArrayList;

public class ViewNotificationAdapter extends RecyclerView.Adapter<ViewNotificationAdapter.MyViewHolder> {

    private Activity activity;
    private int layoutId;
    private ArrayList<Notification> arrayList;

    public ViewNotificationAdapter(Activity activity, int layoutId, ArrayList<Notification> arrayList) {
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
        String str = "<b>Nguyễn Gia Nghiêm </b> và 12 người khác đã bình luận về ảnh của bạn: Đẹp quá tar...";
        holder.content_comment.setText(Html.fromHtml(str));
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
        TextView content_comment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content_comment = itemView.findViewById(R.id.content_commnet);
        }
    }
}
