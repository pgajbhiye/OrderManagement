package com.orion.ordermanagement.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.util.Constants;
import com.orion.ordermanagement.util.FirebaseUtil;
import com.orion.ordermanagement.util.Utils;

public class LoginScreen extends Fragment implements View.OnClickListener {


    private Context context;
    EditText userNameEt, passwordEt;
    private String userName, password = "";
    private CheckBox checkBox;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_screen, null);
        userNameEt = view.findViewById(R.id.userName);
        passwordEt = view.findViewById(R.id.password);
        checkBox = view.findViewById(R.id.checkBox);
        Button login = view.findViewById(R.id.login);
        login.setOnClickListener(this);

        if (!TextUtils.isEmpty(Utils.getPrefString(context, Constants.User_NAME))) {
            String userName = Utils.getPrefString(context, Constants.User_NAME);
            this.userName = userName;
            userNameEt.setText(userName);
        }
        if (!TextUtils.isEmpty(Utils.getPrefString(context, Constants.USER_PASSWORD))) {
            String password = Utils.getPrefString(context, Constants.USER_PASSWORD);
            this.password = password;
            passwordEt.setText(password);
        }

        checkBox.setChecked(Utils.shouldRemember(context));


        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //validate the correct username here
                if (s != null) {
                    userName = s.toString();
                }

            }
        });


        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //save the password
                if (s != null) {
                    password = s.toString();
                }

            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            if (Utils.hasAllLoginFields(userName, password)) {

                handleRememberMe();
                //persist the data in Firebase Db only if the user does not exists in db yet
                FirebaseUtil.createUser(context, userName);

                if (getActivity() != null) {
                    getActivity().finish();
                }
                Intent intent = new Intent(context, OrderActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, "Enter all your details", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleRememberMe() {
        //Remember me
        if (checkBox.isChecked()) {
            Utils.persistString(context, Constants.User_NAME, userName);
            Utils.persistString(context, Constants.USER_PASSWORD, password);
        } else {
            Utils.removeStringKey(context, Constants.User_NAME);
            Utils.removeStringKey(context, Constants.USER_PASSWORD);
        }
    }

}
