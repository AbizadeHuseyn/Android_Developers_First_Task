package com.example.androiddevelopersfirsttask.ui.fragments.container;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.androiddevelopersfirsttask.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class ContainerFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        navController = NavHostFragment.findNavController(Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.fragment_fragment_container)));
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }


}

