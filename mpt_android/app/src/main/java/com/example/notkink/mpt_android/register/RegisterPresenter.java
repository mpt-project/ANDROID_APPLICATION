package com.example.notkink.mpt_android.register;

import com.example.notkink.mpt_android.App;
import com.example.notkink.mpt_android.upload.BillPleaseApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    RegisterView view;
    Call<ResponseBody> pendingCall;

    public RegisterPresenter() {


    }

    public void setView(RegisterView view) {
        this.view = view;
    }

    public void register(String name,
                         String email,
                         String password,
                         String passwordRepeated) {


        if (!password.contentEquals(passwordRepeated)) {
            view.onRegisterFailure("Passwords does not match");
        } else {

            view.showLoading();
            final BillPleaseApiClient client = App.getApp().getBillPleaseApiClient();

            //stop pending call
            if (pendingCall != null) {
                pendingCall.cancel();
            }

            pendingCall = client.registerUser(new RegisterRequest(email, password, name));

            pendingCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    handleRegisterResponse(client, response);
                    pendingCall = null;
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    view.hideLoading();
                    view.onRegisterFailure(String.valueOf(t));
                    pendingCall = null;
                }
            });
        }
    }

    private void handleRegisterResponse(BillPleaseApiClient client, Response<ResponseBody> response) {

        view.hideLoading();
        RegisterResponse registerResponse = client.getResponse(response, RegisterResponse.class);

        if (registerResponse != null) {
            if (response.code() == 200) {
                //see routes/register.js at backend code why 200 appeared here

                view.onRegisterSuccess(registerResponse.message);

            } else {
                //show error message
                view.onRegisterFailure(registerResponse.message);
            }
        } else {
            //something went wrong
            System.err.println("");
            view.onRegisterFailure("Something went wrong, try again later");
        }
    }

}
