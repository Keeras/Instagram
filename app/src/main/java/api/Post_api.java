package api;

import java.util.List;

import model.PostModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Post_api {

    @POST("api/post-auth/addPost")
    Call<Void> addPost(@Body PostModel postModel);

    @GET("api/post-auth/post")
    Call<List<PostModel>> getPost();

}
