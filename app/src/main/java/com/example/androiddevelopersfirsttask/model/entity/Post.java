package com.example.androiddevelopersfirsttask.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "comments")
    private Boolean comments;

    @ColumnInfo(name = "like_count")
    private String likeCount;


    @ColumnInfo(name = "share_count")
    private String shareCount;

    @ColumnInfo(name = "comment_count")
    private String commentCount;

    public Post(
            @NonNull String id,
            String name,
            String description,
            Boolean comments,
            String likeCount,
            String shareCount,
            String commentCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.comments = comments;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.commentCount = commentCount;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getComments() {
        return comments;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public String getCommentCount() {
        return commentCount;
    }
}
