package com.example.mobileprak_tugas5;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("api")
    Call<List<Post>> getPosts();
}
