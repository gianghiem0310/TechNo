package com.example.technosocialapp.fragment.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.ChatScreenActivity;
import com.example.technosocialapp.adapter.ViewAddFriendAdapter;
import com.example.technosocialapp.adapter.chat.ViewChatApdater;
import com.example.technosocialapp.adapter.chat.ViewOnlineApdater;
import com.example.technosocialapp.model.Chat;
import com.example.technosocialapp.model.InforUser;

import java.util.ArrayList;

public class ChatFragment extends AbstractFragment{
    SearchView searchView;
    ArrayList<InforUser> arrayListInfo;
    ViewOnlineApdater viewOnlineApdater;
    LinearLayoutManager linearLayoutManagerNgang;
    RecyclerView recyclerViewNgang;
    ArrayList<Chat> arrayListChat;
    ViewChatApdater viewChatApdater;
    LinearLayoutManager linearLayoutManagerDoc;
    RecyclerView recyclerViewDoc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_chat, container, false);
        anhXa(view);

        setFirst();
        setData();
        setEvent();
        return view;
    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void setData(){
        for (int i = 0; i < 10; i++) {
            arrayListChat.add(new Chat(1));
            InforUser inforUser = new InforUser(i,"Null","Hay ",1,"1","9","1","l","0");
            arrayListInfo.add(inforUser);
        }
        viewChatApdater.notifyDataSetChanged();
        viewOnlineApdater.notifyDataSetChanged();
    }
    private void setFirst(){
        arrayListInfo = new ArrayList<>();
        arrayListChat = new ArrayList<>();
        viewOnlineApdater = new ViewOnlineApdater(R.layout.card_view_online,arrayListInfo,getActivity());
        linearLayoutManagerNgang = new LinearLayoutManager(getActivity());
        linearLayoutManagerNgang.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewNgang.setLayoutManager(linearLayoutManagerNgang);
        recyclerViewNgang.setAdapter(viewOnlineApdater);
        viewChatApdater = new ViewChatApdater(R.layout.card_view_chat,arrayListChat,getActivity());
        linearLayoutManagerDoc = new LinearLayoutManager(getActivity());
        linearLayoutManagerDoc.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDoc.setLayoutManager(linearLayoutManagerDoc);
        recyclerViewDoc.setAdapter(viewChatApdater);
    }
    private void setEvent(){
        searchView.setQueryHint("Tìm kiếm đoạn chat..");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
                searchView.setIconifiedByDefault(true);
            }
        });
        viewChatApdater.setOnClickItemListener(new ViewChatApdater.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                Intent intent = new Intent(getContext(), ChatScreenActivity.class);
                startActivity(intent);
            }
        });
    }
    private void anhXa(View fragment){
        searchView = fragment.findViewById(R.id.search_bar);
        recyclerViewNgang = fragment.findViewById(R.id.recyclerViewNgang);
        recyclerViewDoc = fragment.findViewById(R.id.recyclerViewDoc);
    }
}
