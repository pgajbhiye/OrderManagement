package com.orion.ordermanagement.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.util.Constants;
import com.orion.ordermanagement.util.Utils;

public class RegistrationScreen extends Fragment implements View.OnClickListener {

    private Context context;
    private EditText firstNameET;
    private EditText lastNameEt;
    private EditText emailEt;
    private EditText passwordEt;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_screen, null);
        firstNameET = view.findViewById(R.id.firstName);
        lastNameEt = view.findViewById(R.id.lastName);
        emailEt = view.findViewById(R.id.email);
        passwordEt = view.findViewById(R.id.password);

        Button registerButton = view.findViewById(R.id.register);
        registerButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {

            setData();

            boolean allValidData = true;

            if (!Utils.isNameValid(firstName) && !Utils.isNameValid(lastName) && TextUtils.isEmpty(password) && !Utils.isEmailValid(email)) {
                Toast.makeText(getActivity(), context.getString(R.string.empty_msg_error), Toast.LENGTH_SHORT).show();
                allValidData = false;
            } else if (!Utils.isNameValid(firstName)) {
                allValidData = false;
                Toast.makeText(getActivity(), context.getString(R.string.invalid_first_name_msg), Toast.LENGTH_SHORT).show();
            } else if (!Utils.isNameValid(lastName)) {
                allValidData = false;
                Toast.makeText(getActivity(), context.getString(R.string.invalid_last_name_msg), Toast.LENGTH_SHORT).show();
            } else if (!Utils.isEmailValid(email)) {
                allValidData = false;
                Toast.makeText(getActivity(), context.getString(R.string.invalid_email_msg), Toast.LENGTH_SHORT).show();
            } else if (!Utils.isPasswordValid(password)) {
                allValidData = false;
                Toast.makeText(getActivity(), context.getString(R.string.invalid_password_msg), Toast.LENGTH_LONG).show();
            }


            if (allValidData) {
                Utils.persistBoolean(context, Constants.IS_REGISTERED_USER, true);

                FragmentManager manager = getFragmentManager();
                if (manager != null) {
                    FragmentTransaction transaction = manager.beginTransaction();
                    LoginScreen loginScreen = new LoginScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    loginScreen.setArguments(bundle);
                    transaction.replace(R.id.container, loginScreen);
                    transaction.commit();
                }
            }
        }
    }

    private void setData() {
        if (firstNameET.getText() != null) {
            firstName = firstNameET.getText().toString();
        }

        if (lastNameEt.getText() != null) {
            lastName = lastNameEt.getText().toString();
        }

        if (emailEt.getText() != null) {
            email = emailEt.getText().toString();
        }

        if (passwordEt.getText() != null) {
            password = passwordEt.getText().toString();
        }
    }

}
