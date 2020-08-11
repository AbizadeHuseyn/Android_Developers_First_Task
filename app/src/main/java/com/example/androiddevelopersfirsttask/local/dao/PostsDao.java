package com.example.androiddevelopersfirsttask.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androiddevelopersfirsttask.model.entity.Post;

import java.util.List;

@Dao
public interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Query("select * from posts")
    List<Post> getAllPosts();


}
