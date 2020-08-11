package com.example.androiddevelopersfirsttask.ui.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.local.Database;

public class ProfileFragment extends Fragment {


    private TextView textViewName;
    private TextView textViewFullName;
    private ImageView imageViewProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName = view.findViewById(R.id.text_view_name_in_profile);
        textViewFullName = view.findViewById(R.id.text_view_full_name);
        imageViewProfilePhoto = view.findViewById(R.id.image_view_profile);
        loadImage();

        textViewName.setText(Database.LOGGED_IN_USER_NAME);
        textViewFullName.setText(Database.LOGGED_IN_USER_NAME);

    }

    private void loadImage() {
        String url = "https://i.pinimg.com/originals/36/60/58/366058cd421e6a981e50c6f800abbbd0.png";
        Glide.with(this).
                load(url)
                .centerCrop()
                .into(imageViewProfilePhoto);

    }
}
