package com.example.servey.ViewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

class Mediator {
    private MutableLiveData mutableLiveData ;

    public Mediator() {
        mutableLiveData= new MutableLiveData<>();


    }
}
