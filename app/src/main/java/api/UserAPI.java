package api;

import model.Account;
import model.ImageResponse;
import model.Register;
import model.Response;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserAPI {

    @POST("api/account/register")
    Call<Response> register(@Body Register register);

    @POST("api/account/login")
    Call<Response> login(@Body Account account);
    @Multipart
    @POST("api/restaurant/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

}
