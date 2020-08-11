package com.example.androiddevelopersfirsttask.ui.fragments.registerpartone;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPartOneFragment extends Fragment {

    private NavController navController;

    private UsersDao usersDao;

    private TextView stepOne;
    private ImageButton registerPageOneBackArrowButton;
    private TextView textSignUpToLoginPage;
    private Button nextButton;
    private EditText editTextName;
    private EditText editTextEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_part_one, container, false);
        navController = NavHostFragment.findNavController(this);

        Database database = Database.getDatabase(getContext());
        usersDao = database.getUsersDao();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        registerPageOneBackArrowButton = view.findViewById(R.id.image_button_back_arrow_in_register_one);
        stepOne = view.findViewById(R.id.text_view_stepOne);
        textSignUpToLoginPage = view.findViewById(R.id.text_view_not_the_first_time);
        nextButton = view.findViewById(R.id.button_next);
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextEmail = view.findViewById(R.id.edit_text_email);

        setSpannableString();
        setOnClickListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setSpannableString() {

        String string = "1 /2\nsteps";

        SpannableString spannableString = new SpannableString(string);

        AbsoluteSizeSpan twentyDPString = new AbsoluteSizeSpan(20, true);
        AbsoluteSizeSpan twentyFourDPString = new AbsoluteSizeSpan(24, true);

        spannableString.setSpan(twentyDPString, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(twentyFourDPString, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        stepOne.setText(spannableString);
    }

    private void setOnClickListeners() {

        registerPageOneBackArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();

            }
        });

        textSignUpToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(RegisterPartOneFragmentDirections.actionRegisterPartOneFragmentToLoginFragment());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isUserInputIsValid()) {
                    String enteredName = editTextName.getText().toString().trim();
                    String enteredEmail = editTextEmail.getText().toString().trim();

                    navController.navigate(RegisterPartOneFragmentDirections.actionRegisterPartOneFragmentToRegisterPartTwoFragment(enteredEmail, enteredName));
                }
            }
        });


    }

    private boolean isUserInputIsValid() {
        String enteredName = editTextName.getText().toString().trim();
        String enteredEmail = editTextEmail.getText().toString().trim();
        if (!"".equals(enteredName)) {
            if (isEmailValid()) {
                if (null == usersDao.getUserName(enteredEmail)) {
                    return true;
                } else {
                    Toast.makeText(getContext(), "An account with this email has been already created, try another mail", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Name field is empty!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean isEmailValid() {
        String enteredEmail = editTextEmail.getText().toString().trim();
        Pattern pattern = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+");
        Matcher matcher = pattern.matcher(enteredEmail);
        return matcher.find();
    }


}
