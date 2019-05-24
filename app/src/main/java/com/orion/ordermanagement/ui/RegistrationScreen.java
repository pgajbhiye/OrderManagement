package com.orion.ordermanagement.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.util.Utils;

public class RegistrationScreen extends Fragment implements View.OnClickListener {

    private Context context;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_screen, null);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        Button registerButton = view.findViewById(R.id.register);
        registerButton.setOnClickListener(this);


        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    String firstName = s.toString();
                    if (!Utils.isNameValid(firstName)) {
                        Toast.makeText(getActivity(), "Enter valid name", Toast.LENGTH_SHORT).show();
                    } else {
                        lastName.setFocusable(true);

                    }
                }
            }
        });


        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    String email = s.toString();
                    if (!Utils.isEmailValid(email)) {
                        Toast.makeText(getActivity(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            FragmentManager manager = getFragmentManager();
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, new LoginScreen());
                transaction.commit();
            }
        }
    }
}
