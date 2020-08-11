package com.example.androiddevelopersfirsttask.ui.fragments.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.local.Database;
import com.example.androiddevelopersfirsttask.local.dao.PostsDao;
import com.example.androiddevelopersfirsttask.model.entity.Post;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ShareFragment extends Fragment {


    private TextView textViewName;
    private Button buttonShare;
    private ImageView imageViewCroppedImage;
    private TextInputLayout textInputLayoutDescription;
    private Switch switchComment;
    private ImageView imageViewProfilePhoto;

    private PostsDao postsDao;

    private boolean isImageChosen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName = view.findViewById(R.id.text_view_username);
        buttonShare = view.findViewById(R.id.button_share_button);
        imageViewCroppedImage = view.findViewById(R.id.image_view_cropped_image);
        textInputLayoutDescription = view.findViewById(R.id.messageTextInputLayout);
        switchComment = view.findViewById(R.id.switch_comment_switch);
        imageViewProfilePhoto = view.findViewById(R.id.image_view_profile_in_share);

        textViewName.setText(Database.LOGGED_IN_USER_NAME);
        postsDao = Database.getDatabase(getContext()).getPostsDao();

        setOnClickListeners();
        loadImage();
    }

    private void setOnClickListeners() {

        imageViewCroppedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(requireContext(), ShareFragment.this);
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageChosen) {
                    sharePost();
                } else {
                    Toast.makeText(getContext(), "Please choose a valid picture!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();
                imageViewCroppedImage.setImageURI(resultUri);
                imageViewCroppedImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                isImageChosen = true;
            }
        }
    }


    private void writeImageToInternalStorage(Bitmap bitmap, String id) {

        File file = new File(requireActivity().getFilesDir(), id + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getRandomInteger() {
        int maximum = 999;
        int minimum = 1;
        int num = ((int) (Math.random() * (maximum - minimum))) + minimum;
        String number = Integer.toString(num);

        return number;
    }

    private String getDescriptionText() {
        String description = Objects.requireNonNull(textInputLayoutDescription.getEditText()).getText().toString();
        return description;
    }

    private void sharePost() {
        String id = UUID.randomUUID().toString();
        String name = Database.LOGGED_IN_USER_NAME;
        String description = getDescriptionText();
        boolean commentsActivated = switchComment.isSelected();
        String likeCount = getRandomInteger();
        String shareCount = getRandomInteger();
        String commentCount = getRandomInteger();

        Post post = new Post(id, name, description, commentsActivated, likeCount, shareCount, commentCount);
        postsDao.insert(post);

        writeImageToInternalStorage(((BitmapDrawable) imageViewCroppedImage.getDrawable()).getBitmap(), id);


        Toast.makeText(getContext(), "Post shared!", Toast.LENGTH_SHORT).show();

    }

    private void loadImage() {
        String url = "https://i.pinimg.com/originals/36/60/58/366058cd421e6a981e50c6f800abbbd0.png";
        Glide.with(this).
                load(url)
                .centerCrop()
                .into(imageViewProfilePhoto);

    }
}
