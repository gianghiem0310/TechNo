package com.example.technosocialapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.activity.LoginActivity;
import com.example.technosocialapp.activity.ProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuFragment extends AbstractFragment{
    ConstraintLayout btnDangXuat;
    SharedPreferences sharedPreferences;
    ConstraintLayout header_menu;
    CircleImageView avt_user;
    TextView name_user;
    ProgressBar progressBar;
    String name_shared;
    String avatar_shared;
    View bg_tool_1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_menu, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa(view);
        progessBar(true);
        getDuLieuShared();
        setDuLieu();
        setSuKien();
        progessBar(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.title.setText("Menu");
    }
    private void setDuLieu (){
        name_user.setText(name_shared);
        if(!avatar_shared.equals(Enum.NULL)) {
            Glide.with(getActivity().getLayoutInflater().getContext()).load(avatar_shared).into(avt_user);
        }
        else {
            avt_user.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.avt_mac_dinh, getActivity().getTheme()));
        }
    }
    private void getDuLieuShared(){
        name_shared = sharedPreferences.getString(Enum.NAME_USER,Enum.NULL);
        avatar_shared = sharedPreferences.getString(Enum.IMAGE_USER,Enum.NULL);
    }

    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void setSuKien(){
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().remove(Enum.ID_USER).commit();
                sharedPreferences.edit().remove(Enum.NAME_USER).commit();
                sharedPreferences.edit().remove(Enum.IMAGE_USER).commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        header_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putLong(Enum.ID_USER_VIEW,sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT)).commit();
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
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
    private void anhXa(View fragment){
        btnDangXuat = fragment.findViewById(R.id.btnDangXuat);
        header_menu = fragment.findViewById(R.id.header_menu);
        avt_user = fragment.findViewById(R.id.avt_user);
        name_user = fragment.findViewById(R.id.name_user);
        progressBar = fragment.findViewById(R.id.progessbar);
        bg_tool_1 = fragment.findViewById(R.id.bg_tool_1);
    }
}
