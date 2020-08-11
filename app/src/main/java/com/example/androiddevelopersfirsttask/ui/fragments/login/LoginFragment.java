package com.example.androiddevelopersfirsttask.ui.fragments.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androiddevelopersfirsttask.R;
import com.example.androiddevelopersfirsttask.local.Database;
import com.example.androiddevelopersfirsttask.local.dao.UsersDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private NavController navController;

    private UsersDao usersDao;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private ImageButton loginPageBackArrowButton;
    private TextView textLoginToSignUpPage;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        navController = NavHostFragment.findNavController(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setting views
        loginPageBackArrowButton = view.findViewById(R.id.image_button_back_arrow);
        textLoginToSignUpPage = view.findViewById(R.id.text_view_first_time_here);
        loginButton = view.findViewById(R.id.button_login_button);
        editTextUsername = view.findViewById(R.id.edit_text_username);
        editTextPassword = view.findViewById(R.id.edit_text_password);

        setOnClickListeners();
    }


    private void setOnClickListeners() {

        loginPageBackArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();

            }
        });

        textLoginToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterPartOneFragment());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database dataBase = Database.getDatabase(getContext());
                usersDao = dataBase.getUsersDao();

                String enteredPassword = editTextPassword.getText().toString();
                String enteredUsername = editTextUsername.getText().toString();


                if (isUsernameValid() && (usersDao.getUserName(enteredUsername) != null)) {
                    if (!"".equals(enteredPassword)) {
                        if (enteredPassword.equals(usersDao.getPassword(enteredUsername))) {

                            Database.LOGGED_IN_USER_NAME = usersDao.getName(enteredUsername);

                            Toast.makeText(getContext(), "Welcome " + Database.LOGGED_IN_USER_NAME + "!", Toast.LENGTH_SHORT).show();
                            navController.navigate(LoginFragmentDirections.actionLoginFragmentToContainerFragment());

                        } else {
                            Toast.makeText(getContext(), "password or username is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(),
                                "password field is empty, please,enter the password",
                                Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), "No account found with this email!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private boolean isUsernameValid() {
        Pattern pattern = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+");
        Matcher matcher = pattern.matcher(editTextUsername.getText().toString());
        return matcher.find();
    }
}
