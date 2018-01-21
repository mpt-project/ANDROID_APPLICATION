package com.example.notkink.mpt_android.register;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.notkink.mpt_android.R;
import com.example.notkink.mpt_android.toast.Toaster;

/**
 * Created by Notkink on 01.12.2017.
 */

public class RegisterActivity extends Activity implements RegisterView {


    RegisterPresenter presenter = new RegisterPresenter();
    Toaster toaster = new Toaster();

    ProgressBar progressBar;
    Button signUpButton;

    EditText emailInput, nameInput, passwordInput, passwordRepeatedInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.7));

        presenter.setView(this);

        injectViews();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NavUtils.navigateUpFromSameTask(RegisterActivity.this);
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordRepeated = passwordRepeatedInput.getText().toString();


                presenter.register(name, email, password, passwordRepeated);

            }

        });
    }

    private void injectViews() {
        nameInput = findViewById(R.id.register_name);
        emailInput = findViewById(R.id.register_email);
        passwordInput = findViewById(R.id.register_password);
        passwordRepeatedInput = findViewById(R.id.register_password_repeated);
        progressBar = findViewById(R.id.sign_up_window_progressbar);
        signUpButton = findViewById(R.id.sign_up);
    }

    @Override
    public void onRegisterSuccess(String message) {
        System.err.println("onRegisterSuccess: " + message);
        toaster.showToast(message);
        NavUtils.navigateUpFromSameTask(RegisterActivity.this);
    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        System.err.println("on failure: " + errorMessage);
        toaster.showToast(errorMessage);
    }

    @Override
    public void showLoading() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoading() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
