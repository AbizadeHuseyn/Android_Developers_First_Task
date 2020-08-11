package com.example.androiddevelopersfirsttask.ui.fragments.registerparttwo;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
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
import com.example.androiddevelopersfirsttask.model.entity.User;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPartTwoFragment extends Fragment {

    private NavController navController;

    private UsersDao usersDao;

    private TextView stepTwo;
    private ImageButton registerPageTwoBackArrowButton;
    private Button signUpButton;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_part_two, container, false);
        navController = NavHostFragment.findNavController(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepTwo = view.findViewById(R.id.text_view_stepTwo);
        registerPageTwoBackArrowButton = view.findViewById(R.id.image_button_back_arrow_in_register_two);
        signUpButton = view.findViewById(R.id.button_sign_up);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        editTextConfirmPassword = view.findViewById(R.id.edit_text_confirm_password);

        setSpannableString();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        registerPageTwoBackArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserInputIsValid()) {
                    Database database = Database.getDatabase(getContext());
                    usersDao = database.getUsersDao();

                    String name = RegisterPartTwoFragmentArgs.fromBundle(getArguments()).getName();
                    String email = RegisterPartTwoFragmentArgs.fromBundle(getArguments()).getEmail();
                    String password = editTextPassword.getText().toString();
                    User user = new User(UUID.randomUUID().toString(), name, email, password);

                    Database.LOGGED_IN_USER_NAME = name;

                    usersDao.insert(user);

                    Toast.makeText(getContext(), "Welcome!" + name, Toast.LENGTH_SHORT).show();
                    navController.navigate(RegisterPartTwoFragmentDirections.actionRegisterPartTwoFragmentToContainerFragment());
                }
            }
        });


    }


    private boolean isUserInputIsValid() {
        String enteredPassword = editTextPassword.getText().toString();
        String enteredConfirmPassword = editTextConfirmPassword.getText().toString();

        if (!"".equals(enteredPassword)) {
            if (!"".equals(enteredConfirmPassword)) {
                if (isPasswordValid()) {
                    if (enteredPassword.equals(enteredConfirmPassword)) {
                        return true;
                    } else {
                        Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Password is invalid\nPlease be sure that your password contains\n" +
                            "Capital letter, lowercase letter, number, longer than 8 letters", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Confirm Password field is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Passwords field is empty", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean isPasswordValid() {
        String enteredPassword = editTextPassword.getText().toString();
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(enteredPassword);
        return matcher.find();
    }

    private void setSpannableString() {

        String string = "2 /2\nsteps";

        SpannableString spannableString = new SpannableString(string);

        AbsoluteSizeSpan twentyDPString = new AbsoluteSizeSpan(20, true);
        AbsoluteSizeSpan twentyFourDPString = new AbsoluteSizeSpan(24, true);

        spannableString.setSpan(twentyDPString, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(twentyFourDPString, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        stepTwo.setText(spannableString);
    }


}
