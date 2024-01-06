package com.example.technosocialapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.LoginActivity;

public class MenuFragment extends AbstractFragment{
    Button btnDangXuat;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_menu, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa(view);
        setSuKien();
        return view;
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
    }
    private void anhXa(View fragment){
        btnDangXuat = fragment.findViewById(R.id.btnDangXuat);
    }
}
