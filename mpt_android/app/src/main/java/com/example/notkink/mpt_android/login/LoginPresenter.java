package com.example.notkink.mpt_android.login;

import com.example.notkink.mpt_android.App;
import com.example.notkink.mpt_android.register.RegisterResponse;
import com.example.notkink.mpt_android.upload.BillPleaseApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    LoginView view;

    Call<ResponseBody> pendingCall;

    public void setView(LoginView view) {
        this.view = view;
    }

    public void attemptLogin(String email, String password) {

        final BillPleaseApiClient client = App.getApp().getBillPleaseApiClient();

        view.showLoading();


        //stop pending call
        if (pendingCall != null) {
            pendingCall.cancel();
        }
        //create new call to api
        pendingCall = client.loginUser(new LoginRequest(email, password));

        //execute network call
        pendingCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                handleLoginResponse(client, response);
                pendingCall = null;
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoading();
                view.onLoginFailure(String.valueOf(t));
                pendingCall = null;
            }
        });
    }


    private void handleLoginResponse(BillPleaseApiClient client, Response<ResponseBody> response) {

        view.hideLoading();
        LoginResponse registerResponse = client.getResponse(response, LoginResponse.class);

        if (registerResponse != null) {
            if (response.code() == 200) {
                //see routes/register.js at backend code why 200 appeared here
                App.getApp().userId = registerResponse.id;
                view.onLoginSuccess(registerResponse.message);

            } else {
                //show error message
                view.onLoginFailure(registerResponse.message);
            }
        } else {
            //something went wrong
            view.onLoginFailure("Something went wrong, try again later");
        }
    }
}
