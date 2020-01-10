package com.example.instagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import javax.xml.validation.Validator;

import api.UserAPI;
import model.Account;
import model.ImageResponse;
import model.Register;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

import static strictmode.StrictMode.StrictMode;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRFName, etRUsername, etRPhoneNo, etREmail, etRPassword, etRConfPassword;
    private Button btnRegister;
    private ImageView ivRUpload;
    private TextView tvLinkLogIn;
    private String profileImagepath, profileImageName;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please Select an image", Toast.LENGTH_LONG).show();
            }
            Uri uri = data.getData();
            profileImagepath = getRealPathFromUri(uri);
            previewProfileImage(profileImagepath);
        }

    }

    private void previewProfileImage(String profileImagepath) {
        File file = new File(profileImagepath);
        if (file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivRUpload.setImageBitmap(myBitmap);

        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // will hide the title bar

        setContentView(R.layout.activity_register);

        etRFName = findViewById(R.id.etRFName);
        etRUsername = findViewById(R.id.etRUsername);
        etRPhoneNo = findViewById(R.id.etRPhoneNo);
        etREmail = findViewById(R.id.etREmail);
        etRPassword = findViewById(R.id.etRPassword);
        etRConfPassword = findViewById(R.id.etRConfPassword);
        btnRegister = findViewById(R.id.btnRegister);
        ivRUpload = findViewById(R.id.ivRUpload);
        tvLinkLogIn = findViewById(R.id.tvLinkLogIn);

        tvLinkLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivRUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK); //to browse image
                intent.setType("image/*"); //user now can only select the image
                startActivityForResult(intent, 0);
            }


        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }

    private void registerUser() {
        SaveProfileImageOnly();
        String fullname = etRFName.getText().toString();
        String username = etRUsername.getText().toString();
        String email = etREmail.getText().toString();
        String phonenumber = etRPhoneNo.getText().toString();
        String password = etRPassword.getText().toString();
        String cpassword = etRConfPassword.getText().toString();

        if(fullname.isEmpty()){
            etRFName.setError("Please Enter Full Name");
        }else if (username.isEmpty()){
            etRUsername.setError("Please Enter Username");
        }else if (email.isEmpty()){
            etREmail.setError("Please Enter Email");
        }else if (phonenumber.isEmpty()){
            etRPhoneNo.setError("Please Enter Phone Number");
        }else if (password.isEmpty()){
            etRPassword.setError("Please Enter Password");
        }else if (cpassword.isEmpty()){
            etRConfPassword.setError("Please Enter Confirm Password");
        }
        else if(!etRPassword.getText().toString().equals(etRConfPassword.getText().toString())){
            etRPassword.setError("Please Enter Same Password");
        }
        else {



            Register register = new Register(username,password,fullname,phonenumber,email,profileImageName);

            UserAPI userAPI = Url.getInstance().create(UserAPI.class);
            Call<model.Response> responseCall = userAPI.register(register);

            responseCall.enqueue(new Callback<model.Response>() {
                @Override
                public void onResponse(Call<model.Response> call, Response<model.Response> response) {
                    if(response.body().getSuccess()){
                        Toast.makeText(RegisterActivity.this, "Registered Succesfully", Toast.LENGTH_LONG).show();

                        etRFName.setText("");
                        etRUsername.setText("");
                        etREmail.setText("");
                        etRPhoneNo.setText("");
                        etRPassword.setText("");
                        etRConfPassword.setText("");
                        ivRUpload.setImageResource(R.drawable.upload_icon);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Registeration Failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<model.Response> call, Throwable t) {

                    Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void SaveProfileImageOnly() {
        File file = new File(profileImagepath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

        UserAPI userAPI = Url.getInstance().create(UserAPI.class);
        Call<ImageResponse> responseBodyCall = userAPI.uploadImage(body);

        StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            //After saving an image, retrieve the current name of the image
            profileImageName = imageResponseResponse.body().getFilename();

        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
