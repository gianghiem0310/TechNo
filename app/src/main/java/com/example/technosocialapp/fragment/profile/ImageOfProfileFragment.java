package com.example.technosocialapp.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.album.ViewAvatarAdapter;
import com.example.technosocialapp.adapter.album.ViewBackgroundAdapter;
import com.example.technosocialapp.adapter.album.ViewImagePostAdapter;
import com.example.technosocialapp.model.ImageAvatar;
import com.example.technosocialapp.model.ImageBackground;
import com.example.technosocialapp.model.ImagePost;

import java.util.ArrayList;

public class ImageOfProfileFragment extends AbstractFragment{
    ArrayList<ImageAvatar> listAvatar;
    ArrayList<ImageBackground> listBackground;
    ArrayList<ImagePost> listImagePost;
    ViewAvatarAdapter viewAvatarAdapter;
    ViewBackgroundAdapter viewBackgroundAdapter;
    ViewImagePostAdapter viewImagePostAdapter;
    LinearLayoutManager linearLayoutManagerAVT;
    LinearLayoutManager linearLayoutManagerBG;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerViewAvatar;
    RecyclerView recyclerViewBackground;
    RecyclerView recyclerViewImagePost;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_image_profile, container, false);
        anhXa(view);
        setFirst();
        setData();
        return view;
    }
    private void setData(){
        for (int i = 0; i < 10; i++) {
            listAvatar.add(new ImageAvatar(1,"2"));
            listBackground.add(new ImageBackground(1,"2"));
            listImagePost.add(new ImagePost(1,"2"));
        }
        viewAvatarAdapter = new ViewAvatarAdapter(R.layout.card_view_avatar_profile,listAvatar,getActivity());
        viewBackgroundAdapter = new ViewBackgroundAdapter(R.layout.card_view_background_profile,listBackground,getActivity());
        viewImagePostAdapter = new ViewImagePostAdapter(R.layout.card_view_image_post,listImagePost,getActivity());
        linearLayoutManagerAVT = new LinearLayoutManager(getActivity());
        linearLayoutManagerAVT.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewAvatar.setLayoutManager(linearLayoutManagerAVT);
        recyclerViewAvatar.setAdapter(viewAvatarAdapter);
        linearLayoutManagerBG = new LinearLayoutManager(getActivity());
        linearLayoutManagerBG.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewBackground.setLayoutManager(linearLayoutManagerBG);
        recyclerViewBackground.setAdapter(viewBackgroundAdapter);
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerViewImagePost.setLayoutManager(gridLayoutManager);
        recyclerViewImagePost.setAdapter(viewImagePostAdapter);
        viewAvatarAdapter.notifyDataSetChanged();
        viewBackgroundAdapter.notifyDataSetChanged();
        viewImagePostAdapter.notifyDataSetChanged();
    }
    private void setFirst(){
        listAvatar = new ArrayList<>();
        listImagePost = new ArrayList<>();
        listBackground = new ArrayList<>();
    }
    private void anhXa(View fragment){
        recyclerViewAvatar = fragment.findViewById(R.id.recyclerViewAvatar);
        recyclerViewBackground = fragment.findViewById(R.id.recyclerViewNen);
        recyclerViewImagePost = fragment.findViewById(R.id.recyclerViewAnhPost);
    }
}
