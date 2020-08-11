package com.example.androiddevelopersfirsttask.ui.fragments.home;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.model.entity.Post;

import java.io.File;
import java.io.FileInputStream;

public class ItemPostViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewName;
    private TextView textViewLikeCount;
    private TextView textViewShareCount;
    private TextView textViewCommentCount;
    private ImageView imageViewPostedImage;
    private ImageView imageViewProfilePhoto;


    public ItemPostViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.text_view_username_in_item);
        textViewLikeCount = itemView.findViewById(R.id.text_view_like_count);
        textViewShareCount = itemView.findViewById(R.id.text_view_share_count);
        textViewCommentCount = itemView.findViewById(R.id.text_view_comment_count);
        imageViewPostedImage = itemView.findViewById(R.id.image_view_posted_image);
        imageViewProfilePhoto = itemView.findViewById(R.id.image_view_profile_in_post);

    }

    public void bindItem(Post post, File filesDir) {
        textViewName.setText(post.getName());
        textViewLikeCount.setText(post.getLikeCount());
        textViewCommentCount.setText(post.getCommentCount());
        textViewShareCount.setText(post.getShareCount());
        loadImage();

        try {
            File readFile = new File(filesDir, post.getId() + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(readFile));
            imageViewPostedImage.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadImage() {
        String url = "https://i.pinimg.com/originals/36/60/58/366058cd421e6a981e50c6f800abbbd0.png";
        Glide.with(imageViewProfilePhoto).
                load(url)
                .centerCrop()
                .into(imageViewProfilePhoto);

    }

}
