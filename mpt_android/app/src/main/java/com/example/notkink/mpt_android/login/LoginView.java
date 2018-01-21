package com.example.notkink.mpt_android.login;

public interface LoginView {

    void showLoading();

    void hideLoading();

    void onLoginSuccess(String message);

    void onLoginFailure(String message);
}
