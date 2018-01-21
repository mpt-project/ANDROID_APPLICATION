package com.example.notkink.mpt_android.register;

public interface RegisterView {

    void onRegisterSuccess(String message);

    void onRegisterFailure(String errorMessage);

    void showLoading();

    void hideLoading();
}
