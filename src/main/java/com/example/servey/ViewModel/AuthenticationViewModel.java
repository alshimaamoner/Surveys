package com.example.servey.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.servey.Api.APIManager;
import com.example.servey.Api.Model.Data;
import com.example.servey.Api.Model.LoginResponse;

import java.util.logging.ErrorManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationViewModel extends AndroidViewModel {
    MutableLiveData<Data> mediator;
     MutableLiveData<String> errorMessage;

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    MutableLiveData<Boolean> showLoading;

    public MutableLiveData<Data> getMediator() {
        return mediator;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        mediator= new MutableLiveData<>();
        errorMessage=new MutableLiveData<>();
        showLoading=new MutableLiveData<>();
    }

    public void login(String username, String password) {
        APIManager.getAPIs().getData(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                showLoading.postValue(true);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        showLoading.postValue(false);
                         mediator.postValue(response.body().getData());

                    } else {
                        showLoading.postValue(false);
                       mediator.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showLoading.postValue(false);
                errorMessage.postValue(t.getLocalizedMessage());

            }
        });
    }



}
