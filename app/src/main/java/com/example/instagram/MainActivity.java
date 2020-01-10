package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.UserAPI;
import model.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView link_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //        remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar

        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        link_signup = findViewById(R.id.tvLinkSignUp);

        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth();
            }
        });

    }

    private void auth() {
//        retrieve values
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty()){
            etUsername.setError("Please Enter Username");
        }else if (password.isEmpty()){
            etPassword.setError("Please Enter Password");
        }

//        pass retrived values to model
        Account account = new Account(username,password);


//        API instanciate
        UserAPI userAPI = Url.getInstance().create(UserAPI.class);
        Call<model.Response> responseCall = userAPI.login(account);

        responseCall.enqueue(new Callback<model.Response>() {
            @Override
            public void onResponse(Call<model.Response> call, Response<model.Response> response) {
                if (response.body().getSuccess()){
                    Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Login Failed",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<model.Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}
