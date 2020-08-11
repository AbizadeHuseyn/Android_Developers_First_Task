package com.example.androiddevelopersfirsttask.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.local.Database;
import com.example.androiddevelopersfirsttask.local.dao.PostsDao;
import com.example.androiddevelopersfirsttask.model.entity.Post;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewPosts;

    private PostsDao postsDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        postsDao = Database.getDatabase(getContext()).getPostsDao();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPosts = view.findViewById(R.id.recycler_view_posts);
        configureRecycleView();

    }

    private void configureRecycleView() {
        PostsRecyclerViewAdapter adapter = new PostsRecyclerViewAdapter(requireActivity().getFilesDir());
        recyclerViewPosts.setAdapter(adapter);
        List<Post> posts = postsDao.getAllPosts();
        Collections.reverse(posts);
        adapter.submitList(posts);
    }


}
