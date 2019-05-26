package com.orion.ordermanagement.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.interfaces.ProgressListener;
import com.orion.ordermanagement.util.Utils;

public class MainActivity extends AppCompatActivity implements ProgressListener {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        if (Utils.isUserRegistered(this)) {
            transaction.add(R.id.container, new LoginScreen());
            transaction.commit();
        } else {
            transaction.add(R.id.container, new RegistrationScreen());
            transaction.commit();
        }
    }


    @Override
    public void setProgressVisibility(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
