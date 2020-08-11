package com.example.androiddevelopersfirsttask.ui.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.model.entity.Post;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<ItemPostViewHolder> {

    private List<Post> listOfPosts = new ArrayList<>();
    private File filesDir;


    public PostsRecyclerViewAdapter(File filesDir) {
        this.filesDir = filesDir;
    }

    @NonNull
    @Override
    public ItemPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_post, parent, false);

        return new ItemPostViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPostViewHolder holder, int position) {

        holder.bindItem(listOfPosts.get(position), filesDir);

    }

    @Override
    public int getItemCount() {
        return listOfPosts.size();
    }

    public void submitList(List<Post> posts) {
        listOfPosts.clear();
        listOfPosts.addAll(posts);
        notifyDataSetChanged();
    }

}
