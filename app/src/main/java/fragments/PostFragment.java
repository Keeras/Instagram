package fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.R;

import java.io.File;
import java.io.IOException;


import api.Post_api;
import api.UserAPI;
import model.ImageResponse;
import model.PostModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Global;
import url.Url;

import static android.app.Activity.RESULT_OK;
import static strictmode.StrictMode.StrictMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    private ImageView ivUploadProfile;
    private EditText etCaption;
    private Button btnPost;
    private String profileImagepath, profileImagename;



    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if (resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(getContext(),"Please select an Image", Toast.LENGTH_LONG).show();

            }

                Uri uri = data.getData();
                profileImagepath = getRealPathFromUri(uri);
                Toast.makeText(getContext(),profileImagepath,Toast.LENGTH_LONG).show();
                previewProfileImage(profileImagepath);
        }
    }

    private void previewProfileImage(String profileImagepath) {
        File file = new File(profileImagepath);
        if (file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivUploadProfile.setImageBitmap(myBitmap);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post, container, false);
        ivUploadProfile = view.findViewById(R.id.ivUploadProfile);
        etCaption = view.findViewById(R.id.etCaption);


        ivUploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }


        });

        btnPost = view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

        return view;
    }

    private void savePhoto() {
        saveImageOnly();
        String caption = etCaption.getText().toString();
        if (caption.isEmpty()){
            etCaption.setError("Caption field is empty");
        }

        PostModel postModel = new PostModel(caption, profileImagename);
        Post_api post_api = Url.getInstance().create(Post_api.class);
        Call<Void> responseCall = post_api.addPost(postModel);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Post successful",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getContext(),"Upload faild", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void saveImageOnly() {
        File file = new File(profileImagepath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

        UserAPI userAPI = Url.getInstance().create(UserAPI.class);
        Call<ImageResponse> responseBodyCall = userAPI.uploadImage(body);

        StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            //After saving an image, retrieve the current name of the image
            profileImagename = imageResponseResponse.body().getFilename();

        } catch (IOException e) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
