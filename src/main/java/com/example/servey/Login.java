package com.example.servey;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.servey.Api.Model.Data;
import com.example.servey.Base.BaseActivity;
import com.example.servey.ViewModel.AuthenticationViewModel;

import okhttp3.internal.Util;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class Login extends BaseActivity{
    GifImageView image;
    private TextView mEmail;
    private EditText mTextEmail, password;
    private TextView mPass;
    String email, pass;
    CheckBox checkBox;
    Button Login;
  AuthenticationViewModel viewModel;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        image.setImageResource(R.drawable.email);
        email = mTextEmail.getText().toString();
        pass = password.getText().toString();
        mTextEmail.addTextChangedListener(emailWatcher);
        password.addTextChangedListener(PassWatcher);
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        Login.setOnClickListener(loginListener);
        viewModel= ViewModelProviders.of(activity).get(AuthenticationViewModel.class);
  viewModel.getMediator().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                       if(data!=null){
                            Intent intent=new Intent(Login.this,Events.class);
                            startActivity(intent);
                       }else{
                           viewModel.getErrorMessage();
                       }
            }
        });
    viewModel.getErrorMessage().observe(this, new Observer<String>() {
        @Override
        public void onChanged(String s) {
           if(s!=null){
               viewModel.getErrorMessage();
           }
        }
    });
    viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
           ///not true,false
            if(aBoolean!=null && aBoolean==true){
                showProgressBar(R.string.Loading);
            }else{
                hideProgressBar();
            }
        }
    });
    }
View.OnClickListener loginListener=new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(mTextEmail.getText().toString())){
            mTextEmail.setError("Enter your UserName");
            password.setError("Enter your Password");
        }else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Enter your Password");
        }else{
            viewModel.login(mTextEmail.getText().toString(),password.getText().toString());
        }

    }
};
CompoundButton.OnCheckedChangeListener onCheckedChangeListener= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (compoundButton.isChecked()) {
                password.setTransformationMethod(null);
                image.setImageResource(R.drawable.show);


            } else {
                image.setImageResource(R.drawable.first);
                password.setTransformationMethod(new PasswordTransformationMethod());

            }
            password.setSelection(password.getText().length());
        }
    };
    TextWatcher PassWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            image.setImageResource(R.drawable.pass);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            image.setImageResource(R.drawable.pass);

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() ==1 ) {
              //  image.setImageResource(R.drawable.pass);
                image.animate();
            }else if(editable.length()==0){
                image.setImageResource(R.drawable.email);
            }else{
                image.setImageResource(R.drawable.first);

            }


        }


    };


    TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            image.setImageResource(R.drawable.email);
        }


        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            image.setImageResource(R.drawable.email);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            image.animate();
        }

    };
    public void init() {
        image = findViewById(R.id.logo);
        mEmail = findViewById(R.id.Email);
        mTextEmail = findViewById(R.id.textEmail);
        mPass = findViewById(R.id.Pass);
        password = findViewById(R.id.Password);
        checkBox = findViewById(R.id.checkbox);
        Login=findViewById(R.id.Login);
    }

}
