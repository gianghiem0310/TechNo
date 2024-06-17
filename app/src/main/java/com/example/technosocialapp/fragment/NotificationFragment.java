package com.example.technosocialapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.adapter.ViewNotificationAdapter;
import com.example.technosocialapp.model.AuthenticationAdd;
import com.example.technosocialapp.model.Notification;

import java.util.ArrayList;

public class NotificationFragment extends AbstractFragment{
    private ArrayList<Notification> arrayList;
    private RecyclerView recyclerView;
    private ViewNotificationAdapter adapter;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_notification, container, false);
        anhXa(view);
        setFirst();
        setDuLieu();
        return view;
    }
    private void setDuLieu(){
        for (int i = 0; i < 10; i++) {
            arrayList.add(new Notification(1));
        }
        adapter = new ViewNotificationAdapter(getActivity(),R.layout.card_view_notification,arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void setFirst(){
        arrayList = new ArrayList<>();
    }
    private void anhXa(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewDoc);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.title.setText("Thông báo");
    }
}
