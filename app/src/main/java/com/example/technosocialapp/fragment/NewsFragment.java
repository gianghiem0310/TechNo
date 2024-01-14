package com.example.technosocialapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technosocialapp.R;
import com.example.technosocialapp.adapter.ViewPostAdapter;
import com.example.technosocialapp.adapter.ViewStoryAdapter;
import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.Story;

import java.util.ArrayList;

public class NewsFragment extends AbstractFragment{
    private RecyclerView recyclerViewNgang;
    private RecyclerView recyclerViewDoc;
    private ArrayList<Story> listStory;
    private ViewStoryAdapter storyAdapter;
    private ArrayList<Post> listPost;
    private ViewPostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_news, container, false);
        listStory = new ArrayList<>();
        listPost = new ArrayList<>();
        anhXa(view);
        setDuLieuNgang();
        setDuLieuDoc();
        return view;
    }
    private void setDuLieuNgang(){
        Story story = new Story(1,1,"image","image",11);
        for (int i = 0; i < 6; i++) {
            listStory.add(story);
        }
        storyAdapter = new ViewStoryAdapter(R.layout.card_view_story,getActivity(),listStory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewNgang.setLayoutManager(linearLayoutManager);
        recyclerViewNgang.setAdapter(storyAdapter);
        storyAdapter.notifyDataSetChanged();

    }
    private void setDuLieuDoc(){
        Post post = new Post(1,1,"aa",1,1,1,1,1);
        for (int i = 0; i < 6; i++) {
            listPost.add(post);
        }
        postAdapter = new ViewPostAdapter(R.layout.card_view_post,getActivity(),listPost);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDoc.setLayoutManager(linearLayoutManager);
        recyclerViewDoc.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();


    }
    private void anhXa(View frag){
        recyclerViewNgang = frag.findViewById(R.id.recyclerViewNgang);
        recyclerViewDoc = frag.findViewById(R.id.recyclerViewDoc);
    }
}
